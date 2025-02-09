import { Component, inject, model, signal, importProvidersFrom} from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";
import { of } from "rxjs";
import { off } from "process";

interface Wall {
	x1: number;
	y1: number;
	x2: number;
	y2: number;
  }

@Component({
selector: "rooms-app",
templateUrl: './rooms.component.html',
styleUrls: ['./rooms.component.scss'],
standalone: true,
imports: [FormsModule,HttpClientModule,CommonModule],
})

export class RoomsComponent {
private baseUrl = environment.baseUrl;
selectedUser = 0;
selectedGroup = 0;
selectedRoom = 0;
showRoomForm: boolean = false;
isreg = 0;

get role(): any {
    return this.authService.data.user.role;
  }
offices: Array<{id: number; name: string; address:string  } > = [];
rooms: Array<{id: number; name: string; office: string}> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
users: Array<{id: number; name: string; group: string; groupname: string; } > = [];
selectedRoomId: number | null = null;
filteredOffices: Array<{ id: number; name: string; address: string }> = [];
officeSearch = '';
wallsCount: number = 0;
  walls: Wall[] = [];
officeForm:
	any = {
		name: '',
		address: '',
}

roomForm:
any = {
name: '',
office: '',
walls: [{
	x1: 0,
	y1: 0,
	x2: 0,
	y2: 0
}]
}

closeRoomForm() {
	this.showRoomForm = false;
	this.roomForm.name = '';
	this.roomForm.office = '';
}

openEditRoomForm(room: { id: number, name: string, office: string}) {
	this.selectedRoomId = room.id;
	this.roomForm.name = room.name;
	this.roomForm.id = room.id;
	this.showRoomForm = true;
}

openRoomForm() {
	this.showRoomForm = true;
}

editRoom() {
	if (this.roomForm.name) {
	const headers = new HttpHeaders({
		'Authorization': `Bearer ${this.authService.data.token}`
	});
	this.http.post(this.baseUrl  + `/api/rooms/${this.roomForm.id}`, {headers,
		name: this.roomForm.name,
		walls: this.roomForm.walls
	  }).subscribe((data) => {
		if (data && Object.values(data)[0] === 'ok') {
		const index = this.rooms.findIndex(room => room.id === this.selectedRoomId);
		if (index !== -1) {
			this.rooms[index].name = this.roomForm.name;
		}
		alert('Помещение обновлено успешно');
		this.showRoomForm = false;
		}
	});
	} else {
	alert('Пожалуйста, заполните все поля');
	}
}
updateWalls() {
    this.walls = Array(this.wallsCount)
        .fill(null)
        .map(() => ({ x1: 0, y1: 0, x2: 0, y2: 0 }));
}

constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.data) {
		this.isreg = 1;
		this.officesList();
		}
	else
	{
		console.log('not reged');
	}
};


officesList() {
    this.http.get(this.baseUrl + `/api/offices`, {
      headers: {
        Authorization: `Bearer ${this.authService.data.token}`,
      },
    }).subscribe((data: any) => {
      this.offices = data.map((office: any) => ({
        id: office.id,
        name: office.name,
        address: office.address
      }));
      this.filteredOffices = [...this.offices];
    });
  }

  
  filterOffices() {
	const searchValue = this.officeSearch?.toLowerCase() || '';
  
	if (this.authService.data.user.role === 'ADMIN') {
	  this.filteredOffices = this.offices.filter(office =>
		office.name.toLowerCase().startsWith(searchValue)
	  );
	} else {
	  this.filteredOffices = this.offices.filter(office =>
		this.groups.some(group => group.office === office.name) &&
		office.name.toLowerCase().startsWith(searchValue)
	  );
	}
  }
  
selectOffice(office: { id: number; name: string, address: string }): void {
	this.roomForm.office = office;
	this.officeSearch = office.name;
	this.filteredOffices = [];
	this.roomForm.room = '';
	this.roomsList();
}


addRoom() {
if (this.roomForm.name != "") {
	const headers = new HttpHeaders({
		'Authorization': `Bearer ${this.authService.data.token}`
	  });
	  this.http
	  .post(this.baseUrl + `/api/rooms`, 
		{ 
		  name: this.roomForm.name, 
		  walls: this.roomForm.walls, 
		  officeId: this.officeForm.id
		},
		{ headers }
	  )
	.subscribe((data) => {
		console.log(data);
		Object.keys(data).forEach((key, index) => {
		if (Object.values(data)[0] != "") {
			this.rooms.push({
			id: Object.values(data)[0],
			name: this.roomForm.name,
			office: this.officeForm.name
			});
			alert("Помещение успешно создано");
		}
		});
	});
} else {
	alert("Введите название помещения");
}
}

delRoom(id: number)
{
	const headers = new HttpHeaders({
		'Authorization': `Bearer ${this.authService.data.token}`
	  });
this.http.delete(this.baseUrl + `/api/rooms/${id}`, {headers}).subscribe(  data => {
		(Object.keys(data)).forEach((key, index) => {
		if (Object.values(data)[0]=='ok')
		{
			this.rooms = this.rooms.filter(room => room.id !== id);
			alert("Помещение удалено");
		};
		});
	});
	return false;
}

roomsList() {
	const headers = new HttpHeaders({
		'Authorization': `Bearer ${this.authService.data.token}`
	  });
	  const params = new HttpParams().set('officeId', this.roomForm.office.id);
	  this.http.get(this.baseUrl + '/api/rooms', { headers: headers, params: params })
	.subscribe(data => {
		console.log("Data:" + data);
		(Object.keys(data)).forEach((key, index) => {
			this.rooms.push({
				id: Object.values(data)[index]["id"],
				name: Object.values(data)[index]["name"],
				office: Object.values(data)[index]["officeId"],
			});
		});
	});
}
}
