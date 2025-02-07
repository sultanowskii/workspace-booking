import { Component, inject, model, signal, importProvidersFrom} from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";


@Component({
selector: "office-app",
templateUrl: './offices.component.html',
styleUrls: ['./offices.component.scss'],
standalone: true,
imports: [FormsModule,HttpClientModule,CommonModule],
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

offices: Array<{id: number; name: string;  address: string} > = [];
rooms: Array<{id: number; name: string; office: string; }> = [];
filteredOffices: Array<{ id: number; name: string; address: string }> = [];
selectedOfficeId: number | null = null;
groups: Array<{id: number; name: string; office: string;} > = [];
officeForm:
any = {
oname: '',
oaddress: '',
}

roomForm:
any = {
rname: '',
}

addRoomForm:
any = {
roof: 0,
}
  

openOfficeForm() {
	this.showOfficeForm = true;
}

closeOfficeForm() {
	this.showOfficeForm = false;
	this.officeForm.oname = '';
	this.officeForm.oaddress = '';
}

closeEditOfficeForm() {
	this.showOfficeForm = false;
	this.officeForm.oname = '';
	this.officeForm.oaddress = '';
}

addOffice() {
    if (this.officeForm.oname != "") {
      const off_json = {name: this.officeForm.oname, address: this.officeForm.oaddress};
      this.http
        .post(this.baseUrl + `/api/offices`, off_json, {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        })
        .subscribe((data) => {
          Object.keys(data).forEach((key, index) => {
            if (Object.values(data)[0] == "ok") {
              alert("Офис создан успешно");
            }
          });
        });
    } else {
      alert("Введите название офиса");
    }
  }


	openEditOfficeForm(office: { id: number, name: string }) {
		this.selectedOfficeId = office.id;
		this.officeForm.oname = office.name;
		this.showOfficeForm = true;
	}
	
	editOffice() {
		if (this.officeForm.oname) {
		const office_json = JSON.stringify({
			officeName: this.officeForm.oname,
			id: this.selectedOfficeId
		});
	
		this.http.put(this.baseUrl + `/api/offices`, office_json).subscribe((data) => {
			if (data && Object.values(data)[0] === 'ok') {
			const index = this.offices.findIndex(office => office.id === this.selectedOfficeId);
			if (index !== -1) {
				this.offices[index].name = this.officeForm.oname;
			}
	
			alert('Офис обновлен успешно');
			this.showOfficeForm = false;
			}
		});
		} else {
		alert('Пожалуйста, заполните все поля');
		}
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
  
  delOffice(id: number) {
    let params = new HttpParams().set("id", id);
    this.http.delete(this.baseUrl + `/api/office`, {params}).subscribe((data) => {
      Object.keys(data).forEach((key, index) => {
        if (Object.values(data)[0] == "ok") {
          this.offices = this.offices.filter((office) => office.id !== id);
          alert("Офис удален");
        }
      });
    });
    return false;
  }


  delRoom(id: number) {
    let params = new HttpParams().set("id", id);
    this.http.delete(this.baseUrl + `/api/rooms`, {params}).subscribe((data) => {
      Object.keys(data).forEach((key, index) => {
        if (Object.values(data)[0] == "ok") {
          this.rooms = this.rooms.filter((room) => room.id !== id);
          alert("Помещение удалено");
        }
      });
    });
    return false;
  }
}

