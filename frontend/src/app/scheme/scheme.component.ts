import { Directive, Component, inject, model, signal, importProvidersFrom, ElementRef, AfterViewInit, ViewChild, SimpleChanges } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { CdkDragDrop, CdkDragEnd, CdkDrag, CdkDropList, moveItemInArray } from '@angular/cdk/drag-drop';
import { table } from "console";
import { AuthService } from "../services/auth.service";
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
  walls: Array<{ x1: number, y1: number, x2: number, y2: number; room: number; }> = [];

  private baseUrl = environment.baseUrl;
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
  isAddWorkplaceFormOpen = false;
  isAddMeetingRoomFormOpen = false;
  roomSearch: string = '';
  newWorkplace = {
    x: '',
    y: '',
    height: '',
    width: '',
    mon: 0
  };
  newMeetingRoom = {
    name: '',
    x: '',
    y: '',
    height: '',
    width: ''
  };
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
  workplaceBookingForm: any = { date: null };
  availableDates: string[] = [];
  availableTimes: string[] = [];
  isMyBookingsFormOpen = false;
  filteredWorkplaces: Array<{ id: number; mon: number; x: number, y: number; height: number; width: number; room: string, book: string; }> = [];
  filteredMeetingRooms: Array<{ id: number; name: string; x: number, y: number; height: number; width: number; room: string, book: string; }> = [];

  myBookings: { id: number; employeeId: number; workplaceId: number; date: string; }[] = [];

  closeMyBookingsForm() {
    this.isMyBookingsFormOpen = false;
  }

  addWorkplace() {
    this.workplaces.push({
      id: this.workplaceForm.id,
      mon: this.workplaceForm.numberOfMonitors,
      x: this.workplaceForm.x,
      y: this.workplaceForm.y,
      width: this.workplaceForm.width,
      height: this.workplaceForm.height,
      room: this.workplaceForm.roomId,
    });
    this.updateWorkplace();
  }

  addMeetingRoom() {
    this.meetingRooms.push({
      id: this.meetingRoomForm.id,
      name: this.meetingRoomForm.name,
      x: this.meetingRoomForm.x,
      y: this.meetingRoomForm.y,
      width: this.meetingRoomForm.width,
      height: this.meetingRoomForm.height,
      room: this.meetingRoomForm.roomId
    });
    this.updateMeetingRoom();
  }

  addWall() {
    this.walls.push({
      x1: Number(this.wallsForm.x1),
      y1: Number(this.wallsForm.y1),
      x2: Number(this.wallsForm.x2),
      y2: (this.wallsForm.y2),
      room: this.wallsForm.room,
    });
  }

  newPosition(x: number, x2: number, y: number, y2: number) {
    let new_x = x + x2;
    let new_y = y + y2;
    return { "x": new_x, "y": new_y };
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
  wallsForm: any = {
    x1: 0,
    y1: 0,
    x2: 0,
    y2: 0,
    room: ''
  }
  updateInterval!: Subscription;
  isreg = 0;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.officesList();
      this.groupsList();
      this.usersList();
      this.generateAvailableDates();
      this.generateAvailableTimes();
    } else {
      console.log('not reged');
    }
  };

  ngOnInit(): void {
    this.workplaceList(this.selectedRoom);
    this.meetingRoomList(this.selectedRoom);
    this.getWorkplaceBookings();
    this.updateInterval = interval(15000).subscribe(() => {
      this.getWorkplaceBookings();
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
        headers: this.getAuthHeaders()
      })
      .subscribe((data: any) => {
        this.groups = [];
        data.forEach((group: any) => {
          this.groups.push({
            id: group["id"],
            name: group["name"],
          } as any);
        });
      });
  }

  doWorkplaceBooking() {
    // Проверка, что все обязательные поля заполнены
    if (
      !this.workplaceBookingForm.date
    ) {
      alert("Пожалуйста, заполните все поля.");
      return;
    }

    const booking = {
      workplaceId: this.selectedWorkplace.id,
      date: this.workplaceBookingForm.date,
      employeeId: this.authService.data.user.id,
    };

    // Отправляем запрос на сервер для бронирования
    this.http.post(this.baseUrl + "/api/workplaceBookings", booking, { headers: this.getAuthHeaders() })
      .subscribe(
        (data: any) => {
          if (data && data.success) {
            this.myBookings.push(this.selectedWorkplace.id);
            this.updateAvailableDates(booking.date);
            alert("Бронирование успешно!");
            this.closeWorkplaceBookingModal();
          }
        },
        (error) => {
          console.error(error);
          alert("Ошибка при бронировании.");
        }
      );
  }

  
  toggleParticipant(userId: number) {
    if (this.meetingRoomBookingForm.participants.includes(userId)) {
      this.meetingRoomBookingForm.participants = this.meetingRoomBookingForm.participants.filter((id: any) => id !== userId);
    } else {
      this.meetingRoomBookingForm.participants.push(userId);
    }
    console.log(this.meetingRoomBookingForm.participants);
  }


  doMeetingRoomBooking() {
    console.log(this.meetingRoomBookingForm.participants)
    if (
      !this.meetingRoomBookingForm.date ||
      !this.meetingRoomBookingForm.startTime ||
      !this.meetingRoomBookingForm.endTime ||
      !this.meetingRoomBookingForm.description ||
      !this.meetingRoomBookingForm.participants
    ) {
      alert("Пожалуйста, заполните все поля.");
      return;
    }

    const booking = {
      meetingRoomId: this.selectedMeetingRoom.id,
      date: this.meetingRoomBookingForm.date,
      startTime: this.meetingRoomBookingForm.startTime,
      endTime: this.meetingRoomBookingForm.endTime,
      description: this.meetingRoomBookingForm.description,
      participants: this.meetingRoomBookingForm.participants,
      employeeId: this.authService.data.user.id,
    };

    this.http.post(this.baseUrl + "/api/meetingRoomBookings", booking, { headers: this.getAuthHeaders() })
      .subscribe(
        (data: any) => {
          if (data && data.success) {
            this.isMeetingRoomBooked.push(this.selectedMeetingRoom.id);
            this.updateAvailableDates(booking.date);
            alert("Бронирование успешно!");
            this.closeMeetingRoomBookingModal();
          }
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
  
    console.log('Проверка:', workplaceId, formattedDate);
    console.log('Бронирования:', this.myBookings);
  
    const booking = this.myBookings.find(b => 
      b.workplaceId === workplaceId && new Date(b.date).toISOString().split('T')[0] === formattedDate
    );
  
    return booking
      ? booking.employeeId === this.authService.data.user.id
        ? 'bookedByUser'
        : 'booked'
      : 'available';
  }
  
  getWorkplaceColor(workplaceId: number, date: string): string {
    const status = this.isWorkplaceBooked(workplaceId, date);
    console.log(status);
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
    if (this.newWorkplace.x && this.newWorkplace.y && this.newWorkplace.width && this.newWorkplace.height) {
      const payload = {
        roomId: this.selectedRoom.id,
        numberOfMonitors: this.newWorkplace.mon,
        x: this.newWorkplace.x,
        y: this.newWorkplace.y,
        width: this.newWorkplace.width,
        height: this.newWorkplace.height
      };
      this.http.post(`${this.baseUrl}/api/workplaces`, payload, { headers: this.getAuthHeaders() })
        .subscribe(
          (data: any) => {
            this.workplaces[this.workplaces.length - 1].id = data.id;
            console.log('Сохранено новое рабочее место:', this.newWorkplace);
            this.closeAddWorkplaceForm();
            this.newWorkplace = {
              x: '',
              y: '',
              height: '',
              width: '',
              mon: 0
            };
          },
          error => console.error('Ошибка при сохранении рабочего места:', error)
        );
    }
  }

  saveMeetingRoom() {
    if (this.newMeetingRoom.name && this.newMeetingRoom.x && this.newMeetingRoom.y) {
      const payload = {
        roomId: this.selectedRoom.id,
        name: this.newMeetingRoom.name,
        x: this.newMeetingRoom.x,
        y: this.newMeetingRoom.y,
        width: this.newMeetingRoom.width,
        height: this.newMeetingRoom.height
      };

      this.http.post(`${this.baseUrl}/api/meetingRooms`, payload, { headers: this.getAuthHeaders() })
        .subscribe(
          () => {
            console.log('Сохранена новая переговорная:', this.newMeetingRoom);
            this.closeAddMeetingRoomForm();

            this.newMeetingRoom = {
              name: '',
              x: '',
              y: '',
              height: '',
              width: ''
            };
          },
          error => console.error('Ошибка при сохранении переговорной комнаты:', error)
        );
    }
  }

  updateWorkplace() {
    const params = { numberOfMonitors: this.workplaces[this.workplaces.length - 1].mon, x: this.workplaces[this.workplaces.length - 1].x, y: this.workplaces[this.workplaces.length - 1].y, width: this.workplaces[this.workplaces.length - 1].width, height: this.workplaces[this.workplaces.length - 1].height };
    this.http.put(this.baseUrl + `/api/workplace/${this.workplaces[this.workplaces.length - 1].id}`, { headers: this.getAuthHeaders() }, { params }).subscribe(data => { });
  }

  updateMeetingRoom() {
    const params = { name: this.meetingRooms[this.meetingRooms.length - 1].name, x: this.meetingRooms[this.meetingRooms.length - 1].x, y: this.meetingRooms[this.meetingRooms.length - 1].y, width: this.meetingRooms[this.meetingRooms.length - 1].width, height: this.meetingRooms[this.meetingRooms.length - 1].height };
    this.http.put(this.baseUrl + `/api/meetingRooms/${this.meetingRooms[this.meetingRooms.length - 1].id}`, { headers: this.getAuthHeaders() }, { params }).subscribe(data => { });
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
    if (this.authService.data.user.role === 'ADMIN') {
      this.filteredOffices = [...this.offices];
    } else {
      this.filteredOffices = this.offices.filter((office) => {
        return this.groups.some((group) => {
          return group.allowedOffices.some((allowedOffice) => {
            console.log(`Checking if ${allowedOffice.name} === ${office.name}`);
            return allowedOffice.name === office.name;
          });
        });
      });
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

  filterOfficeName() {
    const searchValue = this.officeSearch?.toLowerCase() || '';
    this.filteredOffices = this.offices.filter(office =>
      office.name.toLowerCase().startsWith(searchValue)
    );
  }

  selectOffice(office: { id: number; name: string }): void {
    this.officeForm.office = office.id;
    this.officeSearch = office.name;
    this.filteredOffices = [];
    this.selectedOffice = office.name;
    this.roomsList(office.id);
  }

  unbookWorkplace(workplace: number) {
    this.http.delete(this.baseUrl + `/api/workplaceBookings?workplaceId=${workplace}`, { headers: this.getAuthHeaders() }).subscribe(data => {
      this.workplaces = this.workplaces.map(item =>
        item.id === workplace ? { ...item, book: '' } : item
      );
      if (Object.values(data)[0] === 'ok') {
        alert("Бронирование отменено");
      }
    }, error => {
      console.log(error);
      alert("Ошибка при отмене бронирования.");
    });
  }

  unbookMeetingRoom(meetingRoom: number) {
    this.http.delete(this.baseUrl + `/api/meetingRoomBookings?meetingRoomId=${meetingRoom}`, { headers: this.getAuthHeaders() }).subscribe(data => {
      this.meetingRooms = this.meetingRooms.map(item =>
        item.id === meetingRoom ? { ...item, book: '' } : item
      );
      if (Object.values(data)[0] === 'ok') {
        alert("Бронирование отменено");
      }
    }, error => {
      console.log(error);
      alert("Ошибка при отмене бронирования.");
    });
  }

  workplaceList(selectedRoom: any) {
    console.log(selectedRoom);
    if (!selectedRoom || !selectedRoom.id) {
      console.error('Selected room or roomId is undefined');
      return;  // Прерываем выполнение функции, если roomId не существует
    }
    console.log(selectedRoom)
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
        this.getWorkplaceBookings();
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
      this.getWorkplaceBookings();
    },
    error: (err) => {
      console.error('Error fetching meeting room data', err);
    }
  });
}

  
  getWorkplaceBookings(): void {
    this.http.get(this.baseUrl + `/api/workplaceBookings`, { headers: this.getAuthHeaders() })
      .subscribe((data: any) => {
        console.log('Полученные бронирования:', data);
  
        this.myBookings = data.map((book: any) => ({
          id: book.id,
          employeeId: book.employeeId,
          workplaceId: book.workplaceId,
          date: book.date
        }));
  
        console.log('Обновленный myBookings:', this.myBookings);
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


  getMeetingRoomBookings(): void {
    this.http.get(this.baseUrl + `/api/meetingRoomBookings`, { headers: this.getAuthHeaders() })
      .subscribe((data: any) => {
        this.meetingRoomBookings = data;

        const userBooks = data.filter((item: any) => item.employeeId === this.user);
        userBooks.forEach((book: any) => {
          this.isMeetingRoomBooked.push(book.meetingRoomId);
        });

        this.meetingRooms = this.meetingRooms.map(item => {
          const bookedItem = userBooks.find((newItem: any) => newItem.meetingRoomId === item.id);
          return bookedItem ? { ...item, book: '1' } : item;
        });
      });
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
  
    const element = event.source.element.nativeElement;
  
    // Получаем окончательные координаты с использованием getBoundingClientRect()
    const rect = element.getBoundingClientRect();
    const newPosX = rect.left;
    const newPosY = rect.top;
  
    // Логируем обновленные координаты
    console.log('Final position for workplace', { x: newPosX, y: newPosY });
  
    // Обновляем координаты рабочего места
    this.workplaces[i].x = newPosX;
    this.workplaces[i].y = newPosY;
  
    const updatePayload = {
      x: newPosX,
      y: newPosY,
      numberOfMonitors: this.workplaces[i].mon,
      width: this.workplaces[i].width,
      height: this.workplaces[i].height
    };
  
    // Логируем payload перед отправкой на сервер
    console.log('Updating workplace with payload:', updatePayload);
  
    this.http.put(`${this.baseUrl}/api/workplaces/${this.workplaces[i].id}`, updatePayload, { headers: this.getAuthHeaders() })
      .subscribe({
        next: (response) => {
          console.log('Позиция рабочего места успешно обновлена');
          // После обновления получаем актуальные данные
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
  
    const element = event.source.element.nativeElement;
  
    // Получаем окончательные координаты с использованием getBoundingClientRect()
    const rect = element.getBoundingClientRect();
    const newPosX = rect.left;
    const newPosY = rect.top;
  
    // Логируем обновленные координаты
    console.log('Final position for meeting room', { x: newPosX, y: newPosY });
  
    // Обновляем координаты переговорной комнаты
    this.meetingRooms[i].x = newPosX;
    this.meetingRooms[i].y = newPosY;
  
    const updatePayload = {
      x: newPosX,
      y: newPosY,
      name: this.meetingRooms[i].name,
      width: this.meetingRooms[i].width,
      height: this.meetingRooms[i].height
    };
  
    // Логируем payload перед отправкой на сервер
    console.log('Updating meeting room with payload:', updatePayload);
  
    this.http.put(`${this.baseUrl}/api/meetingRooms/${this.meetingRooms[i].id}`, updatePayload, { headers: this.getAuthHeaders() })
      .subscribe({
        next: (response) => {
          console.log('Позиция переговорной комнаты успешно обновлена');
          // После обновления получаем актуальные данные
          this.meetingRoomList(room);
        },
        error: (err) => {
          console.error('Ошибка обновления позиции переговорной комнаты', err);
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

  delWorkplace(id: number) {
    this.http.delete(this.baseUrl + `/api/workplaces/${id}`, { headers: this.getAuthHeaders() }).subscribe((data) => {
      Object.keys(data).forEach((key, index) => {
        this.workplaces = this.workplaces.filter((workplace) => workplace.id !== id);
      });
      alert("Рабочее место удалено");
    });
    return false;
  }

  delMeetingRoom(id: number) {
    this.http.delete(this.baseUrl + `/api/meetingRooms/${id}`, { headers: this.getAuthHeaders() }).subscribe((data) => {
      Object.keys(data).forEach((key, index) => {
        this.meetingRooms = this.meetingRooms.filter((meetingRoom) => meetingRoom.id !== id);
      });
      alert("Переговорная удалена");
    });
    return false;
  }

  openWorkplaceBookingModal(table: any) {
    this.selectedWorkplace = table;
    this.isWorkplaceBookingModalOpen = true;
    console.log('table: ', table);
  }

  closeWorkplaceBookingModal() {
    this.isWorkplaceBookingModalOpen = false;
    this.selectedWorkplace = null;
    this.workplaceBookingForm.date = [];
  }

  openMeetingRoomBookingModal(table: any) {
    this.selectedMeetingRoom = table;
    this.isMeetingRoomBookingModalOpen = true;
    console.log('table: ', table);
  }

  closeMeetingRoomBookingModal() {
    this.isMeetingRoomBookingModalOpen = false;
    this.selectedMeetingRoom = null;
    this.meetingRoomBookingForm.date = '';
    this.meetingRoomBookingForm.startTime = '',
      this.meetingRoomBookingForm.endTime = '';
  }

  openAddWorkplaceForm() {
    this.isAddWorkplaceFormOpen = true;
  }

  closeAddWorkplaceForm() {
    this.isAddWorkplaceFormOpen = false;
  }

  openAddMeetingRoomForm() {
    this.isAddMeetingRoomFormOpen = true;
  }

  closeAddMeetingRoomForm() {
    this.isAddMeetingRoomFormOpen = false;
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
