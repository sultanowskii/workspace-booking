import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { of } from "rxjs";


@Component({
  selector: "office-app",
  templateUrl: './offices.component.html',
  styleUrls: ['./offices.component.scss'],
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule],
})


export class OfficesComponent {
  private baseUrl = environment.baseUrl;
  selectedRoom = 0;
  showOfficeForm: boolean = false;
  isreg = 0;

  get role(): any {
    const user = this.authService.data;
    return user ? user.role : null;
  }

  offices: Array<{ id: number; name: string; address: string }> = [];
  rooms: Array<{ id: number; name: string; office: string; }> = [];
  filteredOffices: Array<{ id: number; name: string; address: string }> = [];
  selectedOfficeId: number | null = null;
  groups: Array<{
    id: number; name: string; allowedOffices: {
      id: number,
      name: string,
      address: string;
    }[]
  }> = [];
  officeForm:
    any = {
      name: '',
      address: '',
    }

  roomForm:
    any = {
      name: '',
    }

  addRoomForm:
    any = {
      grof: 0,
    }


  openOfficeForm() {
    this.showOfficeForm = true;
  }

  closeOfficeForm() {
    this.showOfficeForm = false;
    this.officeForm.name = '';
    this.officeForm.address = '';
  }

  currentPage = 1;
  pageSize = 5;

  get paginatedOffices() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.filteredOffices.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages() {
    return Math.ceil(this.filteredOffices.length / this.pageSize);
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


  addOffice() {
    if (this.officeForm.name != "") {
      const off = { name: this.officeForm.name, address: this.officeForm.address };
      this.http
        .post(this.baseUrl + `/api/offices`, off, {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        })
        .subscribe((data) => {
          this.officesList();
          alert("Офис создан успешно");
        });
    } else {
      alert("Введите название офиса");
    }
  }


  openEditOfficeForm(office: { id: number, name: string }) {
    this.selectedOfficeId = office.id;
    this.officeForm.name = office.name;
    this.showOfficeForm = true;
  }

  editOffice() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
    if (this.officeForm.name) {
      this.http.put(this.baseUrl + `/api/offices/${this.selectedOfficeId}`, {
        name: this.officeForm.name,
        address: this.officeForm.address
      }, { headers }).subscribe((data: any) => {
        this.officesList();
        alert('Офис обновлен успешно');
        this.showOfficeForm = false;
        this.selectedOfficeId = null;
      });
    } else {
      alert('Пожалуйста, заполните все поля');
    }
  }

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.groupsList();
      this.officesList();
    }
    else {
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
    this.http
      .get(this.baseUrl + `/api/offices`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
        },
      })
      .subscribe((data: any) => {
        console.log("Offices from API:", data);
        this.offices = [];
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
        return this.groups.some((group) =>
          group.allowedOffices.some((allowedOffice) => {
            console.log(`Checking if ${allowedOffice.name} === ${office.name}`);
            return allowedOffice.name === office.name;
          })
        );
      });
  
      console.log("Filtered offices:", this.filteredOffices);
    }
  }
  

  delOffice(id: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.data.token}`
    });
    this.http.delete(this.baseUrl + `/api/offices/${id}`, { headers }).subscribe((data) => {
      Object.keys(data).forEach((key, index) => {
        this.offices = this.offices.filter((office) => office.id !== id);
      });
      this.officesList();
      alert("Офис удален");
    });
    return false;
  }

}

