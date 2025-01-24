import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { HttpClientModule } from '@angular/common/http';

import { of } from 'rxjs';
import { AuthService } from '../services/auth.service';


@Component({
selector: 'app-workload',
standalone: true,
imports: [HttpClientModule, FormsModule, CommonModule],
templateUrl: './workload.component.html',
styleUrl: './workload.component.css'
})

export class WorkloadComponent {
private baseUrl = environment.baseUrl;
offices: Array<{ id: number; name: string; address: string }> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
rooms: Array<{ id: number; name: string; office: string }> = [];
tables:  Array<{id: number; name: string; visibility: string; mon: number; type: string; position: {x: number, y: number}; length: number; width: number; room: string, book: string;} > = [];
books: Array<{id: number; name: string; date: string; user: string;} > = [];
filteredRooms: Array<{ id: number; name: string; office: string }> = [];
filteredOffices = [...this.offices];
officeSearch = '';
officeLoad: number = 0; // Загруженность офиса
userRooms: Array<{ id: number; name: string; office: string }> = []; // Доступные помещения
roomLoadPercentages: { office: string; room: string; load: number; }[] = [];

officeForm:
any = {
office: '',
}
roomForm:
any = {
room: '',
}
bookingForm: any = {
	date: '',
	datetime: '',
}
	
isreg = 0;
loadreport = '';
office = "";
room="";
roomLoadReport: Array<{ room: string; load: number }> = [];

constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.user) {
		this.isreg = 1;
		this.officeslist();
		this.groupslist();
		this.tableslist();
		this.getBooks();
	}
	};

	groupslist() {
		this.http
		  .get(this.baseUrl + "/api/employeeGroups", {
			headers: {
			  Authorization: `Bearer ${this.authService.user.token}`,
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


getOffice(event: any) {
	this.office = event.officeForm.office;
	this.roomForm.room = ''; 
	this.bookingForm.date = '';
}

getRoom(event: any) {
	this.room = event.roomForm.room;
	this.bookingForm.date = ''; 
}

filterOffices() {
	const searchValue = this.officeSearch.toLowerCase();
	this.filteredOffices = this.offices.filter(office => office.name.toLowerCase().startsWith(searchValue));
}

getAvailableRoomsLoad() {
	this.roomLoadReport = this.rooms
	.filter(room => room.office === this.officeForm.office)
	.map(room => {
		const roomTables = this.tables.filter(table => table.room === room.name);
		const bookedCount = roomTables.filter(table => this.books.some(book => book.name === table.name && book.date === this.bookingForm.date)).length;
		const load = roomTables.length > 0 ? Math.round((bookedCount * 100) / roomTables.length) : 0;
		return { room: room.name, load };
	});
}

officeslist() {
	this.http
	.get(`${this.baseUrl}/api/offices`, {
		headers: {
		Authorization: `Bearer ${this.authService.user.token}`,
		},
	})
	.subscribe((data: any) => {
		data.forEach((office: any) => {
		this.offices.push({
			id: office["id"],
			name: office["name"],
			address: office["address"]
		});
		});
	});
}

roomslist() {
	let params = new HttpParams().set('type', 'rooms');
	this.http.get(this.baseUrl, { params }).subscribe(data => {
	Object.keys(data).forEach((key, index) => {
		this.rooms.push({
		id: Object.values(data)[index]["id"],
		name: Object.values(data)[index]["name"],
		office: Object.values(data)[index]["office"]
		});
	});
	});
}

tableslist() {
	let params = new HttpParams().set('type', 'tables');
	this.http.get(this.baseUrl, { params }).subscribe(data => {
	Object.keys(data).forEach((key, index) => {
		this.tables.push({
		id: Object.values(data)[index]["id"],
		name: Object.values(data)[index]["name"],
		visibility: Object.values(data)[index]["visibility"],
		mon: Object.values(data)[index]["mon"],
		type: Object.values(data)[index]["type"],
		position: { x: Object.values(data)[index]["x"], y: Object.values(data)[index]["y"] },
		length: Object.values(data)[index]["length"],
		width: Object.values(data)[index]["width"],
		room: Object.values(data)[index]["room"],
		book: ''
		});
	});
	});
}

getBooks() {
	let params = new HttpParams().set('type', 'getbooks');
	this.http.get(this.baseUrl, { params }).subscribe(data => {
	Object.keys(data).forEach((key, index) => {
		this.books.push({
		id: Object.values(data)[index]["id"],
		name: Object.values(data)[index]["name"],
		date: Object.values(data)[index]["date"],
		user: Object.values(data)[index]["user"]
		});
	});
	});
}

// Расчет загруженности офиса
calculateOfficeLoad(date: string): void {
	const officeTables = this.tables.filter(table => 
	  table.room && table.room === this.officeForm.office
	);
	const bookedTables = officeTables.filter(table => 
	  this.books.some(book => book.date === date && book.name === table.name)
	).length;
	this.officeLoad = officeTables.length > 0 ? Math.round((bookedTables * 100) / officeTables.length) : 0;
  }
  
  calculateRoomLoad(room: string, date: string): number {
    const tablesInRoom = this.tables.filter((table) => table.room === room);
    const bookedTables = tablesInRoom.filter((table) =>
      this.books.some((book) => book.date === date && book.name === table.name)
    ).length;

    return tablesInRoom.length > 0 ? Math.round((bookedTables * 100) / tablesInRoom.length) : 0;
  }
  
  checkDate(date: string): void {
	if (!date) return;
  
	// Пересчитываем загруженность для всего офиса
	this.calculateOfficeLoad(date);
  
	// Для каждого помещения, вычисляем загруженность, но не сохраняем её в `room`
	this.roomLoadPercentages = this.userRooms.map(room => {
	  return {
		office: room.office,
		room: room.name, // Имена помещений остаются как есть
		load: this.calculateRoomLoad(room.name, date) // Загруженность для помещения
	  };
	});
  }
  
  
  

  
  selectOffice(office: { id: number; name: string }): void {
	this.officeForm.office = office.id;
	this.officeSearch = office.name;
	this.filteredOffices = [];
	this.roomForm.room = '';
	this.bookingForm.date = '';
	this.updateFilteredRooms(); // Обновление доступных помещений
	this.calculateOfficeLoad(this.bookingForm.date); // Пересчет загруженности после выбора офиса
  }
  
  // Методы для расчета загруженности офиса и помещений на выбранную дату

// Обновленный метод для обновления доступных помещений
updateFilteredRooms(): void {
	this.filteredRooms = this.rooms.filter(room => 
	  room.office === this.officeForm.office
	);
	this.userRooms = this.filteredRooms;
  }
  
  // Пример фильтра доступных помещений
  isRoomAccessible(room: string): boolean {
	return true; // Замените на реальную проверку доступности
  }
  

}
