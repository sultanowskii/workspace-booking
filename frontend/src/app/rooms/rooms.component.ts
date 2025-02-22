import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
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
	imports: [FormsModule, HttpClientModule, CommonModule],
})

export class RoomsComponent {
	private baseUrl = environment.baseUrl;
	selectedUser = 0;
	selectedGroup = 0;
	selectedRoom = 0;
	showRoomForm: boolean = false;
	isreg = 0;
	selectedOfficeId: number | null = null;
	get role(): any {
		return this.authService.data.user.role;
	}
	offices: Array<{ id: number; name: string; address: string }> = [];
	rooms: Array<{ id: number; name: string; office: string }> = [];
	groups: Array<{
		id: number; name: string; allowedOffices: {
			id: number,
			name: string,
			address: string;
		}[]
	}> = [];

	users: Array<{ id: number; group: string; username: string; }> = [];
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

	currentPage = 1;
	pageSize = 5;

	get paginatedRooms() {
		const startIndex = (this.currentPage - 1) * this.pageSize;
		return this.rooms.slice(startIndex, startIndex + this.pageSize);
	}

	get totalPages() {
		return Math.ceil(this.rooms.length / this.pageSize);
	}

	prevPage() {
		if (this.currentPage > 1) {
			this.currentPage--;
		}
	}

	nextPage() {
		if (this.currentPage < this.totalPages) {
			this.currentPage++;
		}
	}


	closeRoomForm() {
		this.showRoomForm = false;
		this.roomForm.name = '';
		this.roomForm.office = '';
	}

	openEditRoomForm(room: { id: number, name: string, office: string }) {
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
			this.http.put(this.baseUrl + `/api/rooms/${this.roomForm.id}`, {
				name: this.roomForm.name,
				walls: this.roomForm.walls
			},
				{ headers })
				.subscribe((data) => {
					this.roomsList();
					alert('Помещение обновлено успешно');
					this.showRoomForm = false;
					this.selectedRoomId = null;
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
			this.usersList();
			this.groupsList();
			this.officesList();
		}
		else {
			console.log('not reged');
		}
	};

	
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
			if (this.groups && this.users) {
				const currentUser = this.users.find(user => user.username === this.authService.data.user.username);
				console.log(this.users);
				if (currentUser) {
					console.log("Current user's groupId:", currentUser.group);
					const userGroup = this.groups.find(group => group.id.toString().match(currentUser.group));

					if (userGroup) {
						console.log("User's group:", userGroup);
						this.filteredOffices = this.offices.filter(office =>
							userGroup.allowedOffices.some(allowedOffice =>
								allowedOffice.name === office.name
							) && office.name.toLowerCase().startsWith(searchValue)
						);
					}else{
						console.log('no group');
					}
					console.log(this.filteredOffices);
				}else{
					console.log('no user');
				}
			}else{
				console.log('no groups or users');
			}
		}
	}

	selectOffice(office: { id: number; name: string, address: string }): void {
		this.roomForm.office = office;
		this.officeSearch = office.name;
		this.filteredOffices = [];
		this.roomForm.room = '';
		this.selectedOfficeId = office.id;
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
						walls: this.walls,
						officeId: this.selectedOfficeId
					},
					{ headers }
				)
				.subscribe((data) => {
					console.log(data);
					if (Object.values(data)[0] != "") {
						this.roomsList();
					}
				});
		} else {
			alert("Введите название помещения");
		}
	}

	delRoom(id: number) {
		const headers = new HttpHeaders({
			'Authorization': `Bearer ${this.authService.data.token}`
		});
		this.http.delete(this.baseUrl + `/api/rooms/${id}`, { headers })
			.subscribe(data => {
				this.roomsList();
				alert("Помещение удалено");

			});
		return false;
	}

	roomsList() {
		const headers = new HttpHeaders({
			'Authorization': `Bearer ${this.authService.data.token}`
		});
		const params = new HttpParams().set('officeId', this.selectedOfficeId!);
		this.http.get(this.baseUrl + '/api/rooms', { headers: headers, params: params })
			.subscribe(data => {
				console.log("Data:" + data);
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
}
