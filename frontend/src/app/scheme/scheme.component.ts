import { Directive, Component, inject, model, signal, importProvidersFrom, ElementRef, AfterViewInit, ViewChild, SimpleChanges } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams, HttpErrorResponse } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { CdkDragDrop, CdkDragEnd, CdkDrag, CdkDropList, moveItemInArray } from '@angular/cdk/drag-drop';
import { table } from "console";
import { AuthService } from "../services/auth.service";
import { SchemeService } from "../services/scheme.service";
import { stat } from "fs";
import { interval, Subscription } from "rxjs";

@Component({
  selector: "scheme-app",
  templateUrl: './scheme.component.html',
  styleUrls: ['./scheme.component.scss'],
  standalone: true,
  imports: [FormsModule,
    HttpClientModule,
    CommonModule,
    CdkDropList, CdkDrag],
})

export class SchemeComponent {
  offices: Array<{ id: number; name: string; address: string }> = [];
  rooms: Array<{ id: number; name: string; office: string }> = [];
  workplaces: Array<{ id: number; mon: number; x: number, y: number; width: number; height: number; room: number }> = [];
  meetingRooms: Array<{ id: number; name: string; x: number, y: number; width: number; height: number; room: number }> = [];
  workplaceBookings: Array<{ id: number; name: string; date: string; user: string; }> = [];
  meetingRoomBookings: Array<{ id: number; name: string; date: string; user: string; startTime: string; endTime: string; participants: number[]; description: string }> = [];
  private baseUrl = environment.baseUrl;
  currentWorkplace = { id: 0, x: 0, y: 0, height: 0, width: 0, mon: 0 };
  currentMeetingRoom = { id: 0, name: '', x: 0, y: 0, height: 0, width: 0 };
  role = '';
  userOffice = '';
  lastData: string | null = null;
  selectedWorkplace: any;
  selectedMeetingRoom: any;
  selectedOffice = '';
  isWorkplaceBookingModalOpen = false;
  isMeetingRoomBookingModalOpen = false;
  user = 0;
  filteredRooms: Array<{ id: number; name: string; office: string }> = [];
  filteredOffices = [...this.offices];
  officeSearch = '';
  groups: Array<{
    id: number; name: string; allowedOffices: {
      id: number,
      name: string,
      address: string;
    }[]
  }> = [];
  users: Array<{ id: number; group: string; username: string; }> = [];
  isMeetingRoomBooked: string[] = [];
  userRooms: Array<{ id: number; name: string; office: string }> = [];
  room: { id: number; name: string; office: string } | null = null;
  isWorkplaceFormOpen = false;
  isMeetingRoomFormOpen = false;
  roomSearch: string = '';
  officeForm:
    any = {
      office: '',
    }
  roomForm:
    any = {
      room: '',
    }
  selectedRoom: any;
  meetingRoomBookingForm = {
    description: '',
    date: '',
    startTime: '',
    endTime: '',
    participants: [] as number[]
  };
  isEditingWorkplace = false;
  isEditingMeetingRoom = false;
  workplaceBookingForm: any = { date: null };
  availableDates: string[] = [];
  availableTimes: string[] = [];
  isMyBookingsFormOpen = false;
  filteredWorkplaces: Array<{ id: number; mon: number; x: number, y: number; height: number; width: number; room: string, book: string; }> = [];
  filteredMeetingRooms: Array<{ id: number; name: string; x: number, y: number; height: number; width: number; room: string, book: string; }> = [];
  myBookings: { id: number; employeeId: number; workplaceId: number; date: string }[] = [];
  bookings: { id: number; employeeId: number; workplaceId: number; date: string }[] = [];
  myMRBookings: {
    id: number,
    description: string,
    date: string,
    startTime: string,
    endTime: string,
    meetingRoomId: number,
    employeeId: number
  }[] = [];
  editingRoomId: number = 0;
  isMeetingRoomFound: boolean = false;
  editingWorkplaceId: number = 0;
  isWorkplaceFound: boolean = false;
  selectedBooking: any = null;
  selectedMRBooking: any = null;


  findWorkplaceById(event: number) {
    const foundWorkplace = this.workplaces.find(workplace => workplace.id === event);

    this.isWorkplaceFound = !!foundWorkplace;

    if (foundWorkplace) {
      this.currentWorkplace = { ...foundWorkplace };
    } else {
      this.currentWorkplace = { id: this.editingWorkplaceId, x: 0, y: 0, height: 0, width: 0, mon: 0 };
    }
  }

  findMeetingRoomById(event: number) {
    const foundRoom = this.meetingRooms.find(room => room.id === event);
    this.isMeetingRoomFound = !!foundRoom;

    if (foundRoom) {
      this.currentMeetingRoom = { ...foundRoom };
    } else {
      this.currentMeetingRoom = {
        id: this.editingRoomId,
        name: '',
        x: 0,
        y: 0,
        height: 0,
        width: 0
      };
    }
  }


  closeMyBookingsForm() {
    this.myBookings = [];
    this.myMRBookings = [];
    this.isMyBookingsFormOpen = false;
  }

  openMyBookingsForm() {
    this.isMyBookingsFormOpen = true;
  }

  newPosition(x: number, x2: number) {
    let new_x = x + x2;
    return new_x;
  }

  workplaceForm: any = {
    x: 0,
    y: 0,
    numberOfMonitors: 0,
    roomId: 0,
    height: 0,
    width: 0
  }

  meetingRoomForm: any = {
    x: 0,
    y: 0,
    name: '',
    width: 0,
    height: 0,
    roomId: 0
  }

  updateInterval!: Subscription;
  isreg = 0;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService, private schemeService: SchemeService) {
    if (authService.data) {
      this.isreg = 1;
      this.usersList();
      this.groupsList();
      this.officesList();
      this.generateAvailableDates();
      this.generateAvailableTimes();
    } else {
      console.log('not reged');
    }
  };

  ngOnInit(): void {
    this.workplaceList(this.selectedRoom);
    this.meetingRoomList(this.selectedRoom);
    if (this.authService.data.user.role === 'EMPLOYEE') {
      this.getWorkplaceBookings();
      this.getMeetingRoomBookings();
    }
    this.updateInterval = interval(20000).subscribe(() => {
      this.workplaceList(this.selectedRoom);
      this.meetingRoomList(this.selectedRoom);
      if (this.authService.data.user.role === 'EMPLOYEE') {
        this.getWorkplaceBookings();
        this.getMeetingRoomBookings();
      }
    });
  }

  usersList() {
    this.http
      .get(this.baseUrl + `/api/employees`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
        },
      })
      .subscribe((data: any) => {
        this.users = [];
        for (const key in data) {
          this.users.push({
            id: data[key].id,
            group: data[key].employeeGroupId,
            username: data[key].username,
          });
        }
        this.users.sort((a, b) => a.id - b.id);
      });
  }
  
  groupsList() {
    this.http
      .get(this.baseUrl + `/api/employeeGroups`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
        },
      })
      .subscribe((data: any) => {

        this.groups = data.map((group: any) => ({
          id: group.id,
          name: group.name,
          allowedOffices: group.allowedOffices || []
        }));

      }, (error: any) => {
        console.error("Error fetching employee groups:", error);
      });
  }

  doWorkplaceBooking() {
    if (
      !this.workplaceBookingForm.date
    ) {
      alert("Пожалуйста, заполните все поля.");
      return;
    }

    const currentUser = this.users.find(user => user.username === this.authService.data.user.username);
    if (!currentUser) {
      alert("Не удалось определить сотрудника.");
      return;
    }

    const booking = {
      workplaceId: this.selectedWorkplace.id,
      date: this.workplaceBookingForm.date,
      employeeId: currentUser.id,
    };

    this.http.post(this.baseUrl + "/api/workplaceBookings", booking, { headers: this.getAuthHeaders() })
      .subscribe({
        next: (data: any) => {
          this.myBookings.push({
            id: data.id,
            employeeId: data.employeeId,
            workplaceId: data.workplaceId,
            date: data.date
          });
          this.bookings.push({
            id: data.id,
            employeeId: data.employeeId,
            workplaceId: data.workplaceId,
            date: data.date
          });
          this.updateAvailableDates(booking.date);
          alert("Бронирование успешно!");
          this.closeWorkplaceBookingModal();
        },
        error: (err: HttpErrorResponse) => {
          alert(`Место уже занято`);
          console.error('Ошибка бронирования:', err);
        }
      });
  }
  getWorkplaceBookings(): void {
    this.http.get(this.baseUrl + `/api/workplaceBookings`, { headers: this.getAuthHeaders() })
      .subscribe((data: any) => {
        const currentUser = this.users.find(user => user.username === this.authService.data.user.username);

        if (!currentUser) {
          console.error("Не удалось найти текущего пользователя в users.");
          return;
        }

        this.myBookings = data
          .filter((book: any) => book.employeeId === currentUser.id)
          .map((book: any) => ({
            id: book.id,
            employeeId: book.employeeId,
            workplaceId: book.workplaceId,
            date: book.date
          }));
        this.bookings = data
          .map((book: any) => ({
            id: book.id,
            employeeId: book.employeeId,
            workplaceId: book.workplaceId,
            date: book.date
          }));
      });
  }


  saveBookingChanges() {
    if (!this.selectedBooking) return;

    const updatedData = {
      workplaceId: this.selectedBooking.workplaceId,
      date: this.selectedBooking.date
    };

    this.http.put(this.baseUrl + `/api/workplaceBookings/${this.selectedBooking.id}`, updatedData, { headers: this.getAuthHeaders() })
      .subscribe(
        (response: any) => {
          const index = this.myBookings.findIndex(b => b.id === this.selectedBooking.id);
          const index2 = this.bookings.findIndex(b => b.id === this.selectedBooking.id);
          if (index !== -1) {
            this.myBookings[index] = { ...this.selectedBooking };
            this.bookings[index] = { ...this.selectedBooking };
          }

          this.selectedBooking = null;
          alert('Бронирование рабочего места успешно обновлено!');
        },
        (error) => {
          console.error('Ошибка при обновлении бронирования:', error);
          alert('Ошибка при обновлении бронирования');
        }
      );
  }


  editBooking(booking: any) {
    this.selectedBooking = { ...booking };
  }

  editMRBooking(booking: any) {
    this.selectedMRBooking = { ...booking };
  }

  closeEditForm() {
    this.selectedBooking = null;
    this.selectedMRBooking = null;
  }


  deleteBooking(booking: any) {
    if (confirm('Вы уверены, что хотите удалить бронирование?')) {
      this.http.delete(this.baseUrl + `/api/workplaceBookings/${booking.id}`, { headers: this.getAuthHeaders() })
        .subscribe(
          () => {
            this.myBookings.splice(this.myBookings.indexOf(booking), 1);
            this.bookings.splice(this.bookings.indexOf(booking), 1);
            alert("Бронирование удалено!");
          },
          (error) => {
            console.error(error);
            alert("Ошибка при удалении бронирования.");
          }
        );
    }
  }

  saveMRBookingChanges() {
    if (!this.selectedMRBooking) return;

    const updatedData = {
      description: this.selectedMRBooking.description,
      date: this.selectedMRBooking.date,
      startTime: this.selectedMRBooking.startTime,
      endTime: this.selectedMRBooking.endTime,
      meetingRoomId: this.selectedMRBooking.meetingRoomId,
      participants: this.selectedMRBooking.participants || []
    };

    this.http.put(this.baseUrl + `/api/meetingRoomBookings/${this.selectedMRBooking.id}`, updatedData, { headers: this.getAuthHeaders() })
      .subscribe(
        (response: any) => {
          const index = this.myMRBookings.findIndex(b => b.id === this.selectedMRBooking.id);
          if (index !== -1) {
            this.myMRBookings[index] = { ...this.selectedMRBooking };
          }

          this.selectedMRBooking = null;
          alert('Бронирование переговорной успешно обновлено!');
        },
        (error) => {
          console.error('Ошибка при обновлении бронирования переговорной:', error);
          alert('Ошибка при обновлении бронирования переговорной');
        }
      );
  }


  deleteMRBooking(booking: any) {
    if (confirm('Вы уверены, что хотите удалить бронирование переговорной?')) {
      this.http.delete(this.baseUrl + `/api/meetingRoomBookings/${booking.id}`, { headers: this.getAuthHeaders() })
        .subscribe(
          () => {
            this.myMRBookings.splice(this.myMRBookings.indexOf(booking), 1);
            alert("Бронирование переговорной удалено!");
          },
          (error) => {
            console.error(error);
            alert("Ошибка при удалении бронирования переговорной.");
          }
        );
    }
  }

  getMeetingRoomBookings(): void {
    this.http.get(this.baseUrl + `/api/meetingRoomBookings`, { headers: this.getAuthHeaders() })
      .subscribe((data: any) => {
        const currentUser = this.users.find(user => user.username === this.authService.data.user.username);

        if (!currentUser) {
          console.error("Не удалось найти текущего пользователя в users.");
          return;
        }

        this.myMRBookings = data
          .filter((book: any) => book.employeeId === currentUser.id)
          .map((book: any) => ({
            id: book.id,
            description: book.description,
            date: book.date,
            startTime: book.startTime,
            endTime: book.endTime,
            meetingRoomId: book.meetingRoomId,
            employeeId: book.employeeId
          }));
      });
  }


  toggleParticipant(userId: number) {
    if (this.meetingRoomBookingForm.participants.includes(userId)) {
      this.meetingRoomBookingForm.participants = this.meetingRoomBookingForm.participants.filter((id: any) => id !== userId);
    } else {
      this.meetingRoomBookingForm.participants.push(userId);
    }
  }

  doMeetingRoomBooking() {
    console.log(this.meetingRoomBookingForm.participants);

    if (
      !this.meetingRoomBookingForm.date ||
      !this.meetingRoomBookingForm.startTime ||
      !this.meetingRoomBookingForm.endTime ||
      !this.meetingRoomBookingForm.description
    ) {
      alert("Пожалуйста, заполните все поля.");
      return;
    }

    const currentUser = this.users.find(user => user.username === this.authService.data.user.username);

    if (!currentUser) {
      alert("Ошибка: не удалось определить сотрудника.");
      console.error("Не найден текущий пользователь в users.");
      return;
    }

    const booking = {
      meetingRoomId: this.selectedMeetingRoom.id,
      date: this.meetingRoomBookingForm.date,
      startTime: this.meetingRoomBookingForm.startTime,
      endTime: this.meetingRoomBookingForm.endTime,
      description: this.meetingRoomBookingForm.description,
      participants: this.meetingRoomBookingForm.participants,
      employeeId: currentUser.id,
    };

    this.http.post(this.baseUrl + "/api/meetingRoomBookings", booking, { headers: this.getAuthHeaders() })
      .subscribe(
        (data: any) => {
          this.isMeetingRoomBooked.push(this.selectedMeetingRoom.id);
          this.updateAvailableDates(booking.date);
          alert("Бронирование успешно!");
          this.closeMeetingRoomBookingModal();
        },
        (error) => {
          console.error(error);
          alert("Ошибка при бронировании.");
        }
      );
  }

  updateAvailableDates(selectedDate: string) {
    if (!this.availableDates.includes(selectedDate)) {
      this.availableDates.push(selectedDate);
    }
  }

  isWorkplaceBooked(workplaceId: number, date: string): string {
    const formattedDate = new Date(date).toISOString().split('T')[0];

    const currentUser = this.users.find(user => user.username === this.authService.data.user.username);

    if (!currentUser) {
      console.error("Ошибка: не найден текущий пользователь в users.");
      return 'available';
    }

    const booking = this.bookings.find(b =>
      b.workplaceId === workplaceId && new Date(b.date).toISOString().split('T')[0] === formattedDate
    );

    if (!booking) {
      return 'available';
    }
    if (booking.employeeId === currentUser.id) {
      return 'bookedByUser';
    }
    return 'booked';
  }

  getWorkplaceColor(workplaceId: number, date: string): string {
    const status = this.isWorkplaceBooked(workplaceId, date);
    return status === 'bookedByUser' ? '#add8e6' :
      status === 'booked' ? '#d3d3d3' : '#808080';
  }

  updateFilteredRooms(): void {
    this.filteredRooms = this.rooms.filter(room =>
      room.office === this.officeForm.office
    );
    this.userRooms = this.filteredRooms;
  }

  saveWorkplace() {
    console.log('Проверяем координаты:', this.currentWorkplace.x, this.currentWorkplace.y);
    if (this.currentWorkplace.width && this.currentWorkplace.height) {
      if (this.currentWorkplace.x < 50 || this.currentWorkplace.y < 270 || this.currentWorkplace.x > 1300 || this.currentWorkplace.y > 720) {
        this.currentWorkplace.x = 50;
        this.currentWorkplace.y = 300;
      }
      const payload = {
        roomId: this.selectedRoom.id,
        numberOfMonitors: this.currentWorkplace.mon,
        x: this.currentWorkplace.x,
        y: this.currentWorkplace.y,
        width: this.currentWorkplace.width,
        height: this.currentWorkplace.height
      };

      this.http.post(`${this.baseUrl}/api/workplaces`, payload, { headers: this.getAuthHeaders() })
        .subscribe(
          (data: any) => {
            this.currentWorkplace.id = data.id;
            console.log('Сохранено новое рабочее место:', this.currentWorkplace);
            this.workplaceList(this.selectedRoom);
            this.closeWorkplaceForm();
            this.currentWorkplace = {
              id: 0,
              x: 0,
              y: 0,
              height: 0,
              width: 0,
              mon: 0
            };
          },
          error => {
            alert('Ошибка при сохранении рабочего места, измените координаты')
            console.error('Ошибка при сохранении рабочего места:', error);
          }
        );
    } else {
      console.error('Не все поля заполнены.');
    }
  }

  saveMeetingRoom() {
    console.log('Проверяем координаты:', this.currentWorkplace.x, this.currentWorkplace.y);
    if (this.currentMeetingRoom.name) {
      if (this.currentMeetingRoom.x < 50 || this.currentMeetingRoom.y < 270 || this.currentMeetingRoom.x > 1300 || this.currentMeetingRoom.y > 720) {
        this.currentMeetingRoom.x = 50;
        this.currentMeetingRoom.y = 300;
      }
      const payload = {
        roomId: this.selectedRoom.id,
        name: this.currentMeetingRoom.name,
        x: this.currentMeetingRoom.x,
        y: this.currentMeetingRoom.y,
        width: this.currentMeetingRoom.width,
        height: this.currentMeetingRoom.height
      };

      this.http.post(`${this.baseUrl}/api/meetingRooms`, payload, { headers: this.getAuthHeaders() })
        .subscribe(
          (data: any) => {
            this.currentMeetingRoom.id = data.id;

            console.log('Сохранена новая переговорная:', this.currentMeetingRoom);
            this.meetingRoomList(this.selectedRoom);
            this.closeMeetingRoomForm();

            this.currentMeetingRoom = {
              id: 0,
              name: '',
              x: 0,
              y: 0,
              height: 0,
              width: 0
            };
          },
          error => {
            console.error('Ошибка при сохранении переговорной комнаты:', error);
            alert('Ошибка при сохранении переговорной комнаты, измените координаты');
          }
        );
    }
  }

  updateWorkplace() {
    if (this.currentWorkplace.x < 50 || this.currentWorkplace.y < 270 || this.currentWorkplace.x > 1300 || this.currentWorkplace.y > 720) {
      this.currentWorkplace.x = 50;
      this.currentWorkplace.y = 300;
    }
    const body = {
      numberOfMonitors: Number(this.currentWorkplace.mon),
      x: Number(this.currentWorkplace.x),
      y: Number(this.currentWorkplace.y),
      width: Number(this.currentWorkplace.width),
      height: Number(this.currentWorkplace.height)
    };

    this.http.put(this.baseUrl + `/api/workplaces/${this.currentWorkplace.id}`, body, { headers: this.getAuthHeaders() })
      .subscribe(data => {
        console.log("Workplace updated:", data);
        this.workplaceList(this.selectedRoom);
        alert("Рабочее место обновлено");
      }, error => {
        console.error("Update failed", error);
      });
  }


  updateMeetingRoom() {
    if (this.currentMeetingRoom.x < 50 || this.currentMeetingRoom.y < 270 || this.currentMeetingRoom.x > 1300 || this.currentMeetingRoom.y > 720) {
      this.currentMeetingRoom.x = 50;
      this.currentMeetingRoom.y = 300;
    }
    const body = {
      name: this.currentMeetingRoom.name,
      x: Number(this.currentMeetingRoom.x),
      y: Number(this.currentMeetingRoom.y),
      width: Number(this.currentMeetingRoom.width),
      height: Number(this.currentMeetingRoom.height)
    };

    this.http.put(this.baseUrl + `/api/meetingRooms/${this.currentMeetingRoom.id}`, body, { headers: this.getAuthHeaders() })
      .subscribe(data => {
        console.log("Meeting room updated:", data);
        this.meetingRoomList(this.selectedRoom);
        alert("Переговорная обновлена");
      }, error => {
        console.error("Update failed", error);
      });
  }


  officesList() {
    this.http
      .get(this.baseUrl + `/api/offices`, {
        headers: this.getAuthHeaders()
      })
      .subscribe((data: any) => {
        this.offices = [];
        console.log("Offices from API:", data);
        data.forEach((office: any) => {
          this.offices.push({
            id: office["id"],
            name: office["name"],
            address: office["address"]
          });
        })
        this.filterOffices();
      },
        (error: any) => {
          console.error("Error fetching offices:", error);
          alert("Ошибка при получении данных");
        }
      );
  }

  filterOffices() {
    const searchValue = this.officeSearch?.toLowerCase() || '';
    if (this.authService.data.user.role === 'ADMIN') {
      this.filteredOffices = this.offices.filter(office =>
        office.name.toLowerCase().startsWith(searchValue)
      );
    } else {
      if (this.groups && this.users) {
        const currentUser = this.users.find(user => user.username === this.authService.data.user.username);
        if (currentUser) {
          const userGroup = this.groups.find(group => group.id.toString().match(currentUser.group));

          if (userGroup) {
            this.filteredOffices = this.offices.filter(office =>
              userGroup.allowedOffices.some(allowedOffice =>
                allowedOffice.name === office.name
              ) && office.name.toLowerCase().startsWith(searchValue)
            );
          } else {
            console.log('no group');
          }
        } else {
          console.log('no user');
        }
      } else {
        console.log('no groups or users');
      }
    }
  }

  roomsList(officeId: number) {
    const params = new HttpParams().set('officeId', officeId);
    this.http.get(this.baseUrl + '/api/rooms', { headers: this.getAuthHeaders(), params: params })
      .subscribe(data => {
        this.rooms = [];
        (Object.keys(data)).forEach((key, index) => {
          this.rooms.push({
            id: Object.values(data)[index]["id"],
            name: Object.values(data)[index]["name"],
            office: Object.values(data)[index]["officeId"],
          });
        });
      });
  }


  selectRoom(selectedRoom: any) {
    this.selectedRoom = selectedRoom;
    this.workplaceList(selectedRoom);
    this.meetingRoomList(selectedRoom);
    this.filteredRooms = [];
    this.roomSearch = selectedRoom.name;
  }

  filterRooms() {
    const searchValue = this.roomSearch?.toLowerCase() || '';
    this.filteredRooms = this.rooms.filter(room =>
      room.name.toLowerCase().startsWith(searchValue)
    );
  }

  selectOffice(office: { id: number; name: string }): void {
    this.officeForm.office = office.id;
    this.officeSearch = office.name;
    this.filteredOffices = [];
    this.selectedOffice = office.name;
    this.roomsList(office.id);
  }

  workplaceList(selectedRoom: any) {
    if (!selectedRoom || !selectedRoom.id) {
      console.error('Selected room or roomId is undefined');
      return;
    }
    this.http.get(this.baseUrl + `/api/workplaces`, {
      headers: this.getAuthHeaders(),
      params: { roomId: selectedRoom.id }
    }).subscribe({
      next: (data) => {
        this.workplaces = Object.values(data).map((table: any) => ({
          id: table.id,
          mon: table.numberOfMonitors,
          x: table.x,
          y: table.y,
          width: table.width,
          height: table.height,
          room: table.roomId
        }));
        if (this.authService.data.user.role === 'EMPLOYEE') {
          this.getWorkplaceBookings();
        }
      },
      error: (err) => {
        console.error('Error fetching workplace data', err);
      }
    });
  }

  meetingRoomList(selectedRoom: any) {
    if (!selectedRoom || !selectedRoom.id) {
      console.error('Selected room or roomId is undefined');
      return;  // Прерываем выполнение функции, если roomId не существует
    }

    this.http.get(this.baseUrl + `/api/meetingRooms`, {
      headers: this.getAuthHeaders(),
      params: { roomId: selectedRoom.id }
    }).subscribe({
      next: (data) => {
        this.meetingRooms = Object.values(data).map((room: any) => ({
          id: room.id,
          name: room.name,
          x: room.x,
          y: room.y,
          width: room.width,
          height: room.height,
          room: room.roomId
        }));
        if (this.authService.data.user.role === 'EMPLOYEE') {
          this.getMeetingRoomBookings();
        }
      },
      error: (err) => {
        console.error('Error fetching meeting room data', err);
      }
    });
  }

  checkAndBookMeetingRoom() {
    if (
      !this.meetingRoomBookingForm.date ||
      !this.meetingRoomBookingForm.startTime ||
      !this.meetingRoomBookingForm.endTime ||
      !this.meetingRoomBookingForm.description ||
      this.meetingRoomBookingForm.participants.length === 0
    ) {
      alert("Пожалуйста, заполните все поля.");
      return;
    }

    const newBookingStart = new Date(`${this.meetingRoomBookingForm.date}T${this.meetingRoomBookingForm.startTime}`);
    const newBookingEnd = new Date(`${this.meetingRoomBookingForm.date}T${this.meetingRoomBookingForm.endTime}`);

    const hasConflict = this.meetingRoomBookings.some(existingBooking => {
      if (existingBooking.name !== this.selectedMeetingRoom.name) {
        return false;
      }

      const existingStart = new Date(`${existingBooking.date}T${existingBooking.startTime}`);
      const existingEnd = new Date(`${existingBooking.date}T${existingBooking.endTime}`);

      return (
        (newBookingStart >= existingStart && newBookingStart < existingEnd) || // Начало внутри другого бронирования
        (newBookingEnd > existingStart && newBookingEnd <= existingEnd) || // Конец внутри другого бронирования
        (newBookingStart <= existingStart && newBookingEnd >= existingEnd) // Полностью покрывает другое бронирование
      );
    });

    if (hasConflict) {
      alert("Выбранное время уже занято. Выберите другое.");
      return;
    }

    this.doMeetingRoomBooking();
  }


  generateAvailableDates() {
    this.availableDates = [];
    const today = new Date();
    for (let i = 0; i < 5; i++) {
      const nextDay = new Date(today);
      nextDay.setDate(today.getDate() + i);
      const nextDayString = nextDay.toISOString();
      this.availableDates.push(nextDayString);
    }
  }

  generateAvailableTimes() {
    this.availableTimes = [];
    for (let hour = 0; hour < 24; hour++) {
      const time = `${hour.toString().padStart(2, '0')}:00`
      this.availableTimes.push(time);
    }
  }

  dragEndedNewWorkplace(event: CdkDragEnd, i: number, room: any) {
    if (this.authService.data.user.role === 'EMPLOYEE') {
      this.workplaceList(room);
      return;
    }

    const newPosX = this.newPosition(this.workplaces[i].x, event.distance.x);
    const newPosY = this.newPosition(this.workplaces[i].y, event.distance.y);

    if (newPosX < 40 + this.workplaces[i].width || newPosY < 260 + this.workplaces[i].height || newPosX > 1305 + this.workplaces[i].width || newPosY > 718 + this.workplaces[i].height) {
      alert('Вы вышли за границы комнаты');
    } else {
      this.workplaces[i].x = newPosX;
      this.workplaces[i].y = newPosY;
    }
    const updatePayload = {
      x: this.workplaces[i].x,
      y: this.workplaces[i].y,
      numberOfMonitors: this.workplaces[i].mon,
      width: this.workplaces[i].width,
      height: this.workplaces[i].height
    }

    this.http.put(`${this.baseUrl}/api/workplaces/${this.workplaces[i].id}`, updatePayload, { headers: this.getAuthHeaders() })
      .subscribe({
        next: () => {
          console.log('Позиция рабочего места успешно обновлена');
          this.workplaceList(room);
        },
        error: (err) => {
          console.error('Ошибка обновления позиции рабочего места', err);
        }
      });
  }

  dragEndedNewMeetingRoom(event: CdkDragEnd, i: number, room: any) {
    if (this.authService.data.user.role === 'EMPLOYEE') {
      this.meetingRoomList(room);
      return;
    }

    const newPosX = this.newPosition(this.meetingRooms[i].x, event.distance.x);
    const newPosY = this.newPosition(this.meetingRooms[i].y, event.distance.y);
    if (newPosX < 40 + this.meetingRooms[i].width || newPosY < 260 + this.meetingRooms[i].height || newPosX > 1305 + this.meetingRooms[i].width || newPosY > 718 + this.meetingRooms[i].height) {
      alert('Вы вышли за границы комнаты');
    } else {
      this.meetingRooms[i].x = newPosX;
      this.meetingRooms[i].y = newPosY;
    }

    const updatePayload = {
      x: this.meetingRooms[i].x,
      y: this.meetingRooms[i].y,
      name: this.meetingRooms[i].name,
      width: this.meetingRooms[i].width,
      height: this.meetingRooms[i].height
    };

    this.http.put(`${this.baseUrl}/api/meetingRooms/${this.meetingRooms[i].id}`, updatePayload, { headers: this.getAuthHeaders() })
      .subscribe({
        next: (response) => {
          console.log('Позиция переговорной комнаты успешно обновлена');
          this.meetingRoomList(room);
        },
        error: (err) => {
          console.error('Ошибка обновления позиции переговорной комнаты', err);
          this.meetingRoomList(room);
        }
      });
  }


  getAuthHeaders() {
    return new HttpHeaders({ 'Authorization': `Bearer ${this.authService.data.token}` });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['workplaces']) {
      this.updateWorkplace();
    }
    if (changes['meetingRooms']) {
      this.updateMeetingRoom();
    }
  }

  loadData() {
    this.schemeService.getWorkplaces().subscribe(
      data => {
        if (this.isValidStructure(data)) {
          const newWorkplaces = data.workplaces.filter(
            (wp: any) => !this.workplaces.some(existingWp => existingWp.id === wp.id)
          );

          const newMeetingRooms = data.meetingRooms.filter(
            (mr: any) => !this.meetingRooms.some(existingMr => existingMr.id === mr.id)
          );

          this.workplaces = this.workplaces.concat(newWorkplaces);
          this.meetingRooms = this.meetingRooms.concat(newMeetingRooms);

          newWorkplaces.forEach((workplace: any) => {
            this.currentWorkplace = workplace;
            this.saveWorkplace();
          });

          newMeetingRooms.forEach((meetingRoom: any) => {
            this.currentMeetingRoom = meetingRoom;
            this.saveMeetingRoom();
          });

        } else {
          alert('Проверьте координаты и структуру json-файла');
          console.error('Ошибка: Неверная структура JSON-файла!');
        }
      },
      error => {
        console.error('Ошибка загрузки JSON-файла!', error);
      }
    );
  }


  private isValidStructure(data: any): boolean {
    return (
      data &&
      Array.isArray(data.workplaces) &&
      Array.isArray(data.meetingRooms) &&
      data.workplaces.every((wp: any) =>
        (wp.x < 50 || wp.y < 270 || wp.x > 1300 || wp.y > 720)
        &&
        typeof wp.mon === 'number' &&
        typeof wp.x === 'number' &&
        typeof wp.y === 'number' &&
        typeof wp.width === 'number' &&
        typeof wp.height === 'number' &&
        typeof wp.room === 'number'
      ) &&
      data.meetingRooms.every((mr: any) =>
        (mr.x < 50 || mr.y < 270 || mr.x > 1300 || mr.y > 720)
        &&
        typeof mr.name === 'string' &&
        typeof mr.x === 'number' &&
        typeof mr.y === 'number' &&
        typeof mr.width === 'number' &&
        typeof mr.height === 'number' &&
        typeof mr.room === 'number'
      )
    );
  }
  delWorkplace(id: number) {
    this.http.delete(this.baseUrl + `/api/workplaces/${id}`, { headers: this.getAuthHeaders() }).subscribe(
      () => {
        this.workplaces = this.workplaces.filter((workplace) => workplace.id !== id);
        alert("Рабочее место удалено");
      },
      error => {
        console.error("Ошибка при удалении рабочего места:", error);
        alert("Ошибка при удалении рабочего места");
      }
    );
    return false;
  }

  delMeetingRoom(id: number) {
    this.http.delete(this.baseUrl + `/api/meetingRooms/${id}`, { headers: this.getAuthHeaders() }).subscribe(
      () => {
        this.meetingRooms = this.meetingRooms.filter((meetingRoom) => meetingRoom.id !== id);
        alert("Переговорная удалена");
      },
      error => {
        console.error("Ошибка при удалении переговорной:", error);
        alert("Ошибка при удалении переговорной");
      }
    );
    return false;
  }


  openWorkplaceBookingModal(table: any) {
    this.selectedWorkplace = table;
    this.isWorkplaceBookingModalOpen = true;
  }

  closeWorkplaceBookingModal() {
    this.isWorkplaceBookingModalOpen = false;
    this.selectedWorkplace = null;
    this.workplaceBookingForm.date = null;
  }

  openMeetingRoomBookingModal(table: any) {
    this.selectedMeetingRoom = table;
    this.isMeetingRoomBookingModalOpen = true;
  }

  closeMeetingRoomBookingModal() {
    this.isMeetingRoomBookingModalOpen = false;
    this.selectedMeetingRoom = null;
    this.meetingRoomBookingForm.date = '';
    this.meetingRoomBookingForm.startTime = '',
      this.meetingRoomBookingForm.endTime = '';
  }

  // openWorkplaceForm(isEditingWorkplace: boolean, workplaceId: number) {
  //   this.currentWorkplace.id = workplaceId;
  //   this.isEditingWorkplace = isEditingWorkplace;
  //   this.isWorkplaceFormOpen = true;
  // }

  openAddWorkplaceForm(isEditingWorkplace: boolean) {
    this.isEditingWorkplace = isEditingWorkplace;
    this.isWorkplaceFormOpen = true;
  }

  closeWorkplaceForm() {
    this.isWorkplaceFormOpen = false;
  }

  openMeetingRoomForm(isEditingMeetingRoom: boolean, meetingRoomId: number) {
    this.currentMeetingRoom.id = meetingRoomId;
    this.isEditingMeetingRoom = isEditingMeetingRoom;
    this.isMeetingRoomFormOpen = true;
  }

  openAddMeetingRoomForm(isEditingMeetingRoom: boolean) {
    this.isEditingMeetingRoom = isEditingMeetingRoom;
    this.isMeetingRoomFormOpen = true;
  }

  closeMeetingRoomForm() {
    this.isMeetingRoomFormOpen = false;
  }

  currentPage = 1;
  usersPerPage = 5;

  get paginatedUsers() {
    const startIndex = (this.currentPage - 1) * this.usersPerPage;
    return this.users.slice(startIndex, startIndex + this.usersPerPage);
  }

  totalPages() {
    return Math.ceil(this.users.length / this.usersPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages()) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  ngOnDestroy(): void {
    if (this.updateInterval) {
      this.updateInterval.unsubscribe();
    }
  }

}  
