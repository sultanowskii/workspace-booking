import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient, HttpHeaders } from "@angular/common/http";
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
  officeSearch = '';
  officeLoad: number = 0;
  roomLoadPercentages: { office: string; room: string; load: number; }[] = [];

  officeForm: any = { office: '' };
  bookingForm: any = { date: '' };

  isreg = 0;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.getOffices();
    }
  }

  filterOffices() {
    const searchValue = this.officeSearch.toLowerCase();
    this.filteredOffices = this.offices.filter(office => office.name.toLowerCase().startsWith(searchValue));
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
    this.http.get(`${this.baseUrl}/api/occupancy/rooms`, { headers })
      .subscribe((data: any) => {
        this.roomLoadPercentages = data.map((room: any) => ({
          office: room.officeName,
          room: room.name,
          load: room.loadPercentage
        }));
      });
  }
}
