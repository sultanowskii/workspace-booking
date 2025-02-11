import { Component } from '@angular/core';
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
  officeLoad: number = 0;
  roomLoadPercentages: { office: string; room: string; load: number; }[] = [];
  room: { id: number; name: string; office: string } | null = null;
  officeForm: any = { office: '' };
  bookingForm: any = { date: '' };
  roomSearch: string = '';
  isreg = 0;


  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.getOffices();
    }
  }

  filterRooms() {
    const searchValue = this.roomSearch?.toLowerCase() || '';
    this.filteredRooms = this.rooms.filter(room =>
      room.name.toLowerCase().startsWith(searchValue)
    );
  }

  filterOffices() {
    const searchValue = this.officeSearch?.toLowerCase() || '';
    this.filteredOffices = this.offices.filter(office =>
      office.name.toLowerCase().startsWith(searchValue)
    );
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
    this.getOfficeOccupancy(office.id);
  }

  selectRoom(room: { id: number; name: string; office: string }) {
    this.room = room;
    this.getRoomOccupancy();
  }

  getOfficeOccupancy(officeId: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
    this.http.get(`${this.baseUrl}/api/occupancy/offices/${officeId}`, { headers })
      .subscribe((data: any) => {
        this.officeLoad = data.loadPercentage;
      });
  }

  checkDate(date: string): void {
    if (this.officeForm.office && date) {
      this.getAllRoomOccupancies();
    }
  }

  getAllRoomOccupancies() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });

    let params = new HttpParams();
    params = params.set('officeId', this.officeForm.office);
    params = params.set('date', this.bookingForm.date);

    this.http.get(`${this.baseUrl}/api/occupancy/rooms`, { headers: headers, params: params })
      .subscribe((data: any) => {
        this.roomLoadPercentages = data.map((room: any) => ({
          office: room.officeName,
          room: room.name,
          load: room.loadPercentage
        }));
      });
  }


  //   const foundRoom = this.rooms.find(room => room.name === this.roomNameSearch);

  //   getRoomOccupancy() {
  //     if (!this.room) {
  //       console.warn('No room selected.');
  //       return;
  //     }
  //     if (foundRoom) {
  //       this.room = foundRoom;
  //     }
  //     const headers = new HttpHeaders({
  //       'Authorization': `Bearer ${this.authService.data.token}`
  //     });

  //     let params = new HttpParams();
  //     params = params.set('officeId', this.officeForm.office);
  //     params = params.set('date', this.bookingForm.date);

  //     this.http.get(`${this.baseUrl}/api/occupancy/rooms/${this.room.id}`, {
  //         headers: headers,
  //         params: params
  //       })
  //       .subscribe({
  //         next: (data: any) => {
  //           this.roomLoadPercentages = data.map((room: any) => ({
  //             office: room.officeName,
  //             room: room.name,
  //             load: room.loadPercentage
  //           }));
  //         },
  //         error: (error) => {
  //           console.error('Error fetching room occupancy:', error);
  //         }
  //       });
  //   }
  // }
  // getRoomOccupancyByName() {
  //   if (!this.roomSearch) {
  //     console.warn('Введите название помещения.');
  //     return;
  //   }
  // const foundRoom = this.rooms.find(room => room.name === this.roomSearch);

  //   if (foundRoom) {
  //     this.room = foundRoom;
  //     this.getRoomOccupancy();
  //   } else {
  //     console.warn(`Помещение "${this.roomSearch}" не найдено.`);
  //     this.room = null;
  //   }
  // }

  getRoomOccupancy() {
    if (!this.room) {
      console.warn('No room selected.');
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });

    let params = new HttpParams();
    params = params.set('officeId', this.officeForm.office);
    params = params.set('date', this.bookingForm.date);

    this.http.get(`${this.baseUrl}/api/occupancy/rooms/${this.room.id}`, {
      headers: headers,
      params: params
    })
      .subscribe({
        next: (data: any) => {
          this.roomLoadPercentages = data.map((room: any) => ({
            office: room.officeName,
            room: room.name,
            load: room.loadPercentage
          }));
        },
        error: (error) => {
          console.error('Error fetching room occupancy:', error);
        }
      });
  }
}

