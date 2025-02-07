import { Component, inject, model, signal, importProvidersFrom} from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";
import { BehaviorSubject, of } from "rxjs";


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
rooms: Array<{id: number; name: string; office: string; }> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
users: Array<{id: number; name: string; group: string; groupname: string; } > = [];
selectedRoomId: number | null = null;
filteredOffices = new BehaviorSubject<any>(null);
officeSearch = '';

officeForm:
	any = {
		id: undefined,
		name: '',
		address: '',
}

roomForm:
any = {
rname: '',
walls: {
	"x1": 0,
	"y1": 0,
	"x2": 0,
	"y2": 0
}
}

closeRoomForm() {
	this.showRoomForm = false;
	this.roomForm.rname = '';
	this.roomForm.office = '';
}

openEditRoomForm(room: { id: number, name: string, office: string }) {
	this.selectedRoomId = room.id;
	this.roomForm.rname = room.name;
	this.showRoomForm = true;
}

editRoom() {
	if (this.roomForm.rname) {
	const room_json = JSON.stringify({
		room_name: this.roomForm.rname,
		id: this.selectedRoomId
	});

	this.http.post(this.baseUrl  + "/api/rooms", room_json).subscribe((data) => {
		if (data && Object.values(data)[0] === 'ok') {
		const index = this.rooms.findIndex(room => room.id === this.selectedRoomId);
		if (index !== -1) {
			this.rooms[index].name = this.roomForm.rname;
		}
		alert('Помещение обновлено успешно');
		this.showRoomForm = false;
		}
	});
	} else {
	alert('Пожалуйста, заполните все поля');
	}
}


constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.data) {
		this.isreg = 1;
	}
	else
	{
		console.log('not reged');
	}
};


filterOffices() {
	if (this.authService.data.user.role === 'ADMIN'){
		const offices = [...this.offices];
		this.filteredOffices.next(offices);
	} else {
		this.filteredOffices.next(this.offices.filter((office) => {
			return this.groups.some((group) => group.office === office.name);
		}));
	}
}

selectOffice(office: { id: number; name: string }): void {
	this.officeForm.office = office;
	this.officeSearch = office.name;
	this.filteredOffices.next([]);
	this.roomForm.room = '';
}


addRoom() {
if (this.roomForm.rname != "") {
	const room_json = {name: this.roomForm.rname, walls: this.roomForm.walls, officeId: this.officeForm.id};
	this.http
	.post(this.baseUrl + `/api/rooms`, room_json, {
		headers: {
		Authorization: `Bearer ${this.authService.data.token}`,
		},
	})
	.subscribe((data) => {
		Object.keys(data).forEach((key, index) => {
		if (Object.values(data)[0] != "") {
			this.rooms.push({
			id: Object.values(data)[0],
			name: this.roomForm.rname,
			office: this.officeForm.name,
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
let params = new HttpParams()
	.set('id',id);
this.http.get(this.baseUrl, {params}).subscribe(  data => {
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
}
