import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

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
	private selectedFile: File | null = null;
	workplaces: Array<{ id: number; mon: number; x: number, y: number; width: number; height: number; room: number }> = [];
	meetingRooms: Array<{ id: number; name: string; x: number, y: number; width: number; height: number; room: number }> = [];
	workplaceBookings: Array<{ id: number; name: string; date: string; user: string; }> = [];
	meetingRoomBookings: Array<{ id: number; name: string; date: string; user: string; startTime: string; endTime: string; participants: number[]; description: string }> = [];
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
	users: Array<{ id: number; group: string; username: string; }> = [];
	selectedRoomId: number | null = null;
	filteredOffices: Array<{ id: number; name: string; address: string }> = [];
	officeSearch = '';
	officeForm:
		any = {
			name: '',
			address: '',
		}

	roomForm:
		any = {
			name: '',
			office: ''
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
				walls: [{ x1: 0, y1: 0, x2: 0, y2: 0 }]
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
					} else {
						console.log('no group');
					}
					console.log(this.filteredOffices);
				} else {
					console.log('no user');
				}
			} else {
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
						officeId: this.selectedOfficeId,
						walls: [{ x1: 0, y1: 0, x2: 0, y2: 0 }]
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
			this.rooms.sort((a, b) => a.id - b.id);
	}

	getAuthHeaders() {
		return new HttpHeaders({ 'Authorization': `Bearer ${this.authService.data.token}` });
	}
	
	importRoom(file: File) {
		const reader = new FileReader();
	
		reader.onload = (event: any) => {
			try {
				const jsonString = event.target.result as string;
				let data = JSON.parse(jsonString);
	
				const processCoordinates = (items: any[]): any[] => {
					if (Array.isArray(items)) {
						return items.map(item => {
							if (typeof item === 'object' && item !== null && item.hasOwnProperty('x') && item.hasOwnProperty('y') && (item.x < 50 || item.y < 270)) {
								return {
									...item,
									x: item.x + 50,
									y: item.y + 300
								};
							}
							return item;
						});
					}
					return items;
				};
	
				if (typeof data === 'object' && data !== null) {
					if (Array.isArray(data.workplaces)) {
						data.workplaces = processCoordinates(data.workplaces);
					}
	
					if (Array.isArray(data.meetingRooms)) {
						data.meetingRooms = processCoordinates(data.meetingRooms);
					}
				} else {
					console.error("Ошибка: JSON должен быть объектом.");
					alert("Ошибка: JSON должен быть объектом.");
					return;
				}
	
				const modifiedJsonString = JSON.stringify(data);
				const modifiedFile = new File([modifiedJsonString], file.name, { type: 'application/json' });
	
				const formData = new FormData();
				formData.append('file', modifiedFile);
	
				this.http.post(this.baseUrl + `/api/imports`, formData, {
					headers: this.getAuthHeaders()
				}).subscribe({
					next: (response) => {
						console.log('Ответ пришел')
						this.roomsList();
						console.log('Room layout imported successfully');
						alert('Room layout imported successfully!');
					},
					error: (error) => {
						console.error('Error importing room layout', error);
						alert('Error importing room layout: ' + error.message);
					}
				});
	
			} catch (error) {
				console.error("Ошибка при обработке JSON:", error);
				alert("Ошибка при обработке JSON: " + error);
			}
		};
	
		reader.onerror = (error) => {
			console.error("Ошибка при чтении файла:", error);
			alert("Ошибка при чтении файла: " + error);
		};
	
		reader.readAsText(file);
	}

	onFileSelected(event: any) {
		this.selectedFile = event.target.files[0];
		if (!this.selectedFile) {
			console.warn('No file selected.');
		}
	}

	loadData() {
		if (this.selectedFile) {
			console.log('Запрос отправлен');
			this.importRoom(this.selectedFile);
		} else {
			alert('Выберите файл');
		}
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
			return;
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



}
