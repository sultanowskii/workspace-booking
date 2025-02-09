import { Directive, Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {CdkDragDrop, CdkDragEnd, CdkDrag, CdkDropList, moveItemInArray} from '@angular/cdk/drag-drop';
import { table } from "console";
import { AuthService } from "../services/auth.service";

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
  offices: Array<{id: number; name: string; address: string}> = [];
  rooms: Array<{id: number; name: string; office: string}> = [];
  workplaces:  Array<{id: number; name: string; visibility: string; mon: number; position: {x: number, y: number}; length: number; width: number; room: string, book: string;} > = [];
  meetingRooms:  Array<{id: number; name: string; visibility: string; mon: number; position: {x: number, y: number}; length: number; width: number; room: string, book: string;} > = [];
  workplaceBookings: Array<{id: number; name: string; date: string; user: string;} > = [];
  meetingRoomBookings: Array<{id: number; name: string; date: string; user: string;} > = [];
  walls: Array<{visibility: string; position: {x1: number, y1: number, x2: number, y2: number}; room: string;} > = [];

  private baseUrl = environment.baseUrl;
  role = '';
  userOffice = '';
  selectedWorkplace: any;
  selectedMeetingRoom: any;
  isWorkplaceBookingModalOpen = false;
  isMeetingRoomBookingModalOpen = false;
  user = '';
  filteredRooms: Array<{ id: number; name: string; office: string }> = [];
  filteredOffices = [...this.offices];
  officeSearch = '';
  groups: Array<{id: number; name: string; office: string;} > = [];
  isWorkplaceBooked: string[] = [];
  isMeetingRoomBooked: string[] = [];
  userRooms: Array<{ id: number; name: string; office: string }> = []; 
  isAddWorkplaceFormOpen = false;
  isAddMeetingRoomFormOpen = false;

  newWorkplace = {
    name: '',
    x: '',
    y: '',
    length: '',
    width: '',
    mon: 0
  };
  newMeetingRoom = {
    name: '',
    x: '',
    y: '',
    length: '',
    width: '',
    mon: 0
  };
  officeForm:
  any = {
  office: '',
  }
  roomForm:
  any = {
  room: '',
  }
  meetingRoomBookingForm: any = {date: null, selectedTimes:[]};
  workplaceBookingForm: any = {date: null};
  availableDates: Date[] = [];
  availableTimes: string[] = [];
    isMyBookingsFormOpen = false;
  
    myBookings: any[] = [];
  
    closeMyBookingsForm() {
      this.isMyBookingsFormOpen = false;
    }
  
  addWorkplace() {
      this.workplaces.push({
          id: this.workplaceForm.id,
          name: this.workplaceForm.name,
          visibility: 'visible',
          mon: Number(this.workplaceForm.mon),
          position: {x: Number(this.workplaceForm.x), y:Number(this.workplaceForm.y)},
          width:this.workplaceForm.width,
          length:this.workplaceForm.length,
          room: this.workplaceForm.room,
          book: '',
      });
      this.updateWorkplace();
  }

  addMeetingRoom() {
    this.meetingRooms.push({
        id: this.meetingRoomForm.id,
        name: this.meetingRoomForm.name,
        visibility: 'visible',
        mon: Number(this.meetingRoomForm.mon),
        position: {x: Number(this.meetingRoomForm.x), y:Number(this.meetingRoomForm.y)},
        width:this.meetingRoomForm.width,
        length:this.meetingRoomForm.length,
        room: this.meetingRoomForm.room,
        book: '',
    });
    this.updateMeetingRoom();
}

  addWall() {
      this.walls.push({
          visibility: 'visible',
          position: {x1: Number(this.wallsForm.x1), y1:Number(this.wallsForm.y1), x2:Number(this.wallsForm.x2), y2:(this.wallsForm.y2)},
          room: this.wallsForm.room,
      });
      this.updateWalls();
  }

  newPosition(x: number, x2: number, y: number, y2: number) {
      let new_x = x + x2;
      let new_y = y + y2;
      return {"x": new_x, "y": new_y};
  }
  workplaceForm: any = {
      x: 0,
      y: 0,
      mon: 0,
      name: '',
      length: 0,
      width: 0
  }

  meetingRoomForm: any = {
      x: 0,
      y: 0,
      mon: 0,
      name: ''
  }
  wallsForm: any = {
      x1: 0,
      y1: 0,
      x2: 0, 
      y2: 0,
      room: ''
  }

  isreg = 0;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
  if (authService.data) {
      this.isreg = 1;
      this.officesList();
      this.groupsList();
      this.workplaceList();
      this.meetingRoomList();
      this.getWorkplaceBookings();
      this.getMeetingRoomBookings();
      this.generateAvailableDates();
      this.generateAvailableTimes();
      this.workplaceBookingForm.date = this.availableDates[0];
          } else {
              console.log('not reged');
          }
      };
  
  groupsList() {
      this.http
        .get(this.baseUrl + `/api/employeeGroups`, {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        })
        .subscribe((data: any) => {
          data.forEach((group: any) => {
            this.groups.push({
              id: group["id"],
              name: group["name"],
            } as any);
          });
        });
        }

  doWorkplaceBooking() {
  if (this.workplaceBookingForm.date !== "") {
    let date = this.workplaceBookingForm.date;

    const book = {
      book_name: this.selectedWorkplace, 
      book_date: date,
      user: this.user
    };

    this.http.post(this.baseUrl+"/api/workplaceBookings", book).subscribe(data => {
      if (data && Object.values(data)[0] > 0) {
        this.isWorkplaceBooked.push(this.selectedWorkplace);
        this.checkDate(date);
        alert("Бронирование успешно!");
      }
    }, error => {
      console.log(error);
      alert("Ошибка при бронировании.");
    });
  }
}

doMeetingRoomBooking() {
  if (this.meetingRoomBookingForm.date !== "" || this.meetingRoomBookingForm.datetime !== "") {
    let date = this.meetingRoomBookingForm.date;
    let time = "00:00";
    if (this.meetingRoomBookingForm.datetime !== "") {
      date = this.meetingRoomBookingForm.datetime.split("T")[0];
      time = this.meetingRoomBookingForm.datetime.split("T")[1];
    }

    const book = {
      book_name: this.selectedMeetingRoom, 
      book_date: date, 
      book_time: time, 
      user: this.user
    };

    this.http.post(this.baseUrl+"/api/meetingRoomBookings", book).subscribe(data => {
      if (data && Object.values(data)[0] > 0) {
        this.isMeetingRoomBooked.push(this.selectedMeetingRoom);
        this.checkDate(date);
        alert("Бронирование успешно!");
      }
    }, error => {
      console.log(error);
      alert("Ошибка при бронировании.");
    });
  }
}

  updateFilteredRooms(): void {
      this.filteredRooms = this.rooms.filter(room => 
        room.office === this.officeForm.office
      );
      this.userRooms = this.filteredRooms;
    }
    

  updateWorkplace() {
      var tables = this.workplaces;
      const body = {tables: tables};
      this.http.put(this.baseUrl + '/api/workplace?workplaceid=${workplace}', body).subscribe(data => {});
  }

  updateMeetingRoom() {
    var tables = this.meetingRooms;
    const body = {tables: tables};
    this.http.put(this.baseUrl + '/api/meetingRoom?meetingRoomid=${meetingRoom}', body).subscribe(data => {});
}

  updateWalls() {
      var walls = this.walls;
      const body = {walls: walls};
      this.http.put(this.baseUrl, body).subscribe(data => {});
  }

  officesList() {
    this.http
      .get(this.baseUrl + `/api/offices`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
        },
      })
      .subscribe((data: any) => {
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
      console.log("Groups from API:", this.groups);
		return this.groups.some((group) => group.office === office.name);
	  });
	}
  }
  
  roomsList() {
      this.http.get(this.baseUrl + `/api/rooms`).subscribe(data => {
          (Object.keys(data)).forEach((key, index) => {
              this.rooms.push({
                  id: Object.values(data)[index]["id"],
                  name: Object.values(data)[index]["name"],
                  office: Object.values(data)[index]["office"]
              });
          });
      });
  }

unbookWorkplace(workplace: number) {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.authService.data.token}`
  });
  this.http.delete(this.baseUrl + `/api/workplaceBookings?workplaceId=${workplace}`, {headers}).subscribe(data => {
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
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.authService.data.token}`
  });
  this.http.delete(this.baseUrl + `/api/workplaceBookings?meetingRoomId=${meetingRoom}`, {headers}).subscribe(data => {
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


  workplaceList() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
  this.http.get(this.baseUrl + `/api/workplaces`, {headers}).subscribe(data => {
    this.workplaces = Object.values(data).map((table: any) => ({
      id: table.id,
      name: table.name,
      visibility: table.visibility,
      mon: table.mon,
      type: table.type,
      position: { x: table.position.x, y: table.position.y },
      width: table.width,
      length: table.length,
      room: table.room,
      book: '' 
    }));
    this.getWorkplaceBookings();
  });
}

meetingRoomList() {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.authService.data.token}`
  });
  this.http.get(this.baseUrl + `/api/meetingRooms`, {headers}).subscribe(data => {
    this.meetingRooms = Object.values(data).map((table: any) => ({
      id: table.id,
      name: table.name,
      visibility: table.visibility,
      mon: table.mon,
      type: table.type,
      position: { x: table.position.x, y: table.position.y },
      width: table.width,
      length: table.length,
      room: table.room,
      book: '' 
    }));
    this.getWorkplaceBookings();
  });
}

  getWorkplaceBookings(): void {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
  this.http.get(this.baseUrl + `/api/workplaceBookings`, {headers}).subscribe(data => {
    const userBooks = Object.values(data).filter((item: any) => item.user === this.user);
    userBooks.forEach((book: any) => {
      this.isWorkplaceBooked.push(book.name);
    });
    this.workplaces = this.workplaces.map(item => {
      const bookedItem = userBooks.find(newItem => newItem.name === item.name);
      return bookedItem ? { ...item, book: '1' } : item;
    });
  });
}

getMeetingRoomBookings(): void {
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${this.authService.data.token}`
  });
  this.http.get(this.baseUrl + `/api/meetingRoomBookings`, {headers}).subscribe(data => {
    const userBooks = Object.values(data).filter((item: any) => item.user === this.user);
    userBooks.forEach((book: any) => {
      this.isMeetingRoomBooked.push(book.name);
    });
    this.meetingRooms = this.meetingRooms.map(item => {
      const bookedItem = userBooks.find(newItem => newItem.name === item.name);
      return bookedItem ? { ...item, book: '1' } : item;
    });
  });
}

  generateAvailableDates(){
      this.availableDates = [];
      const today = new Date();
      for (let i = 0; i < 5; i++){
          const nextDay = new Date(today);
          nextDay.setDate(today.getDate() + i);
          this.availableDates.push(nextDay);
      }
  }

  generateAvailableTimes(){
    this.availableTimes = [];
    for (let hour = 9; hour < 18; hour++){
      const time = `${hour.toString().padStart(2, '0')}:00`
        this.availableTimes.push(time);
    }
  }
  

selectOffice(office: any) {
  this.officeForm.office = office;
  this.filteredOffices = [];
  this.officeSearch = '';
    if (office.rooms){
        this.workplaceForm.room = office.rooms[0].name;  ///?
        this.roomForm.room = this.workplaceForm.room
    }
}

onRoomChange(){
  this.roomForm.room = this.workplaceForm.room ///?
}

  checkDate(date: Date) {
  console.log('Selected date:', date);
}

  dragEndedNewWorkplace(event: any, i:number){
      this.workplaces[i].position = event.source.getFreeDragPosition();
  }

  dragEndedNewMeetingRoom(event: any, i:number){
    this.meetingRooms[i].position = event.source.getFreeDragPosition();
}

delWorkplace(i: number){
  this.workplaces.splice(i, 1);
}
delMeetingRoom(i: number){
  this.meetingRooms.splice(i, 1);
}

openWorkplaceBookingModal(table:any){
    this.selectedWorkplace = table;
    this.isWorkplaceBookingModalOpen = true;
    console.log('table: ', table);
}

closeWorkplaceBookingModal(){
    this.isWorkplaceBookingModalOpen = false;
    this.selectedWorkplace = null;
    this.workplaceBookingForm.date = [];
}

openMeetingRoomBookingModal(table:any){
  this.selectedMeetingRoom = table;
  this.isMeetingRoomBookingModalOpen = true;
  console.log('table: ', table);
}

closeMeetingRoomBookingModal(){
  this.isMeetingRoomBookingModalOpen = false;
  this.selectedMeetingRoom = null;
  this.meetingRoomBookingForm.date = [];
  this.meetingRoomBookingForm.selectedTime = [];
}

  onTimeChange(time:string, event:any) {
  if (event.target.checked) {
    this.meetingRoomBookingForm.selectedTimes.push(time);
  } else {
    this.meetingRoomBookingForm.selectedTimes = this.meetingRoomBookingForm.selectedTimes.filter((t: string) => t !== time);
  }
}

  bookWorkplace(){
    console.log('Забронировано:', this.selectedWorkplace, ' на дату: ', this.workplaceBookingForm.date);
      this.selectedWorkplace.book = '1';
      this.closeWorkplaceBookingModal()
  }

  bookMeetingRoom(){
    console.log('Забронировано:', this.selectedMeetingRoom, ' на дату: ', this.meetingRoomBookingForm.date, ' время: ', this.meetingRoomBookingForm.selectedTimes);
      this.selectedMeetingRoom.book = '1';
      this.closeMeetingRoomBookingModal()
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

  saveWorkplace() {
    if (this.newWorkplace.name && this.newWorkplace.x && this.newWorkplace.y) {
      console.log('Сохранено новое рабочее место:', this.newWorkplace);
      this.closeAddWorkplaceForm();
      
      this.newWorkplace = {
        name: '',
        x: '',
        y: '',
        length: '',
        width: '',
        mon: 0
      };
    }
  }

  saveMeetingRoom() {
    if (this.newMeetingRoom.name && this.newMeetingRoom.x && this.newMeetingRoom.y) {
      console.log('Сохранена новая переговорная:', this.newMeetingRoom);
      this.closeAddMeetingRoomForm();
      
      this.newMeetingRoom = {
        name: '',
        x: '',
        y: '',
        length: '',
        width: '',
        mon: 0
      };
    }
  }
}
