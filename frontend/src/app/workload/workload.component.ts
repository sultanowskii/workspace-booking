import { Component, provideExperimentalZonelessChangeDetection } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { HttpClientModule } from '@angular/common/http';
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
  rooms: Array<{ id: number; name: string; office: string }> = [];
  filteredOffices = [...this.offices];
  filteredRooms = [...this.rooms];
  officeSearch = '';
  currentPage = 1;
  itemsPerPage = 5; 
  officeLoad: number = 0;
  roomLoadPercentages: { office: string; room: string; load: number; }[] = [];
  roomsLoadPercentages: { office: string; room: string; load: number; }[] = [];
  room: { id: number; name: string; office: string } | null = null;
  officeForm: any = { office: 0 };
  bookingForm: any = { date: '' };
  roomSearch: string = '';
  selectedRoomLoad: number | null = null;
  isreg = 0;
  groups: Array<{
		id: number; name: string; allowedOffices: {
			id: number,
			name: string,
			address: string;
		}[]
	}> = [];

	users: Array<{ id: number; group: string; username: string; }> = [];

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.usersList();
      this.groupsList();
      this.getOffices();
      this.route.paramMap.subscribe(params => {
        const officeIdFromRoute = params.get('officeId');
        if (officeIdFromRoute) {
          this.officeForm.office = +officeIdFromRoute;
        }
      });
    }
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

  filterRooms() {
    this.roomsList();
    const searchValue = this.roomSearch?.toLowerCase() || '';
    this.filteredRooms = this.rooms.filter(room =>
      room.name.toLowerCase().startsWith(searchValue)
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

  roomsList() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
    const params = new HttpParams().set('officeId', this.officeForm.office!);
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

  getOffices() {
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

  selectOffice(office: { id: number; name: string }): void {
    this.officeForm.office = office.id;
    this.officeSearch = office.name;
    this.filteredOffices = [];
    this.bookingForm.date = '';
  }

  selectRoom(room: { id: number; name: string; office: string }) {
    this.room = room;
    this.roomSearch = room.name
    console.log(this.roomSearch);
    this.getRoomOccupancy();
  }

  getOfficeOccupancy(officeId: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });

    let params = new HttpParams();
    params = params.set('date', this.bookingForm.date);

    this.http.get(`${this.baseUrl}/api/occupancy/offices/${officeId}`, { headers,  params: params})
      .subscribe((data: any) => {
        console.log(data)
        this.officeLoad = data.rate*100
      });
  }

  checkDate(date: string): void {
    if (this.officeForm.office && date) {
      this.getAllRoomOccupancies();
      
    this.getOfficeOccupancy(this.officeForm.office);
    }
  }

  getAllRoomOccupancies() {
    if (!this.officeForm.office || !this.bookingForm.date) {
      console.warn('Офис или дата не выбраны.');
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });

    let params = new HttpParams();
    params = params.set('officeId', this.officeForm.office);
    params = params.set('date', this.bookingForm.date);

    this.http.get(`${this.baseUrl}/api/occupancy/rooms`, { headers: headers, params: params })
      .subscribe((data: any) => {
        this.roomsLoadPercentages = data.map((room: any) => ({
          office: room.officeName,
          room: room.name,
          load: room.rate*100
        }));
      });
  }

  getRoomOccupancy() {
    if (!this.roomSearch) {
      console.warn('Введите название помещения.');
      return;
    }

    const foundRoom = this.rooms.find(room => room.name === this.roomSearch);

    if (foundRoom) {
      this.room = foundRoom;
    } else {
      console.warn(`Помещение "${this.roomSearch}" не найдено.`);
      this.room = null;
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });

    let params = new HttpParams();
    params = params.set('id', this.room.id);
    params = params.set('date', this.bookingForm.date);

    this.http.get(`${this.baseUrl}/api/occupancy/rooms/${this.room.id}`, {
      headers: headers,
      params: params
    })
      .subscribe({
        next: (data: any) => {
          console.log(data)
          this.roomLoadPercentages = [];
            this.roomLoadPercentages.push({
              office: data.id,
              room: data.name,
              load: data.rate
            });
          console.log(this.roomLoadPercentages);
        },
        error: (error) => {
          console.error('Error fetching room occupancy:', error);
        }
      });
    this.filteredRooms = []
  }
  getRoomLoad(): number | null {
    const selectedRoom = this.roomLoadPercentages.find(r => r.room === this.roomSearch);
    return selectedRoom ? selectedRoom.load*100 : null;
  }

  get paginatedWorkloads() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.roomLoadPercentages.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages() {
    return Math.ceil(this.roomLoadPercentages.length / this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }
}