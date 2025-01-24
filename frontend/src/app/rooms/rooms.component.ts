import { Component, inject, model, signal, importProvidersFrom} from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";


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
    return this.authService.user.role;
  }
offices: Array<{id: number; name: string; address:string  } > = [];
rooms: Array<{id: number; name: string; office: string; }> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
users: Array<{id: number; name: string; group: string; groupname: string; } > = [];
selectedRoomId: number | null = null; // ID редактируемого офиса
filteredOffices: Array<{ id: number; name: string; address: string }> = [];
officeForm:
any = {
oname: '',
oaddress: '',
}

roomForm:
any = {
rname: '',
}

openRoomForm() {
	this.showRoomForm = true;
}

closeRoomForm() {
	this.showRoomForm = false;
	this.roomForm.rname = '';
	this.roomForm.office = '';
}

closeEditRoomForm() {
	this.showRoomForm = false;
	this.roomForm.rname = '';
	this.roomForm.office = '';
}

	// addOffice() {
	// 	if (this.officeForm.oname ) {//&& this.officeForm.oaddress
	// 	const office_json = JSON.stringify({
	// 		office_name: this.officeForm.oname,
	// 		// office_address: this.officeForm.oaddress,
	// 	});
	
	// 	this.http.post(this.baseUrl, office_json).subscribe((data) => {
	// 		if (data && Object.values(data)[0] === 'ok') {
	// 		// Добавление нового офиса в массив
	// 		this.offices.push({
	// 			id: Object.values(data)[1],  // предполагаем, что сервер возвращает id нового офиса
	// 			name: this.officeForm.oname,
	// 			// address: this.officeForm.oaddress,
	// 		});
	
	// 		alert('Офис создан успешно');
	// 		this.showOfficeForm = false; // Закрываем форму после успешного добавления
	// 		}
	// 	});
	// 	} else {
	// 	alert('Пожалуйста, заполните все поля');
	// 	}
	// }

	openEditRoomForm(room: { id: number, name: string, office: string }) {
		// Заполняем форму данными выбранного офиса
		this.selectedRoomId = room.id; // Сохраняем ID офиса для редактирования
		this.roomForm.rname = room.name;
		// this.officeForm.oaddress = office.address; // Если есть адрес
		this.showRoomForm = true; // Показываем форму редактирования
	}
	
	editRoom() {
		if (this.roomForm.rname) { // Проверка, чтобы название офиса не было пустым
		const room_json = JSON.stringify({
			room_name: this.roomForm.rname,
			// office_address: this.officeForm.oaddress, // Если хотите редактировать адрес
			id: this.selectedRoomId // Добавляем ID офиса для обновления
		});
	
		this.http.post(this.baseUrl  + "/api/rooms", room_json).subscribe((data) => {
			if (data && Object.values(data)[0] === 'ok') {
			// Обновляем офис в массиве
			const index = this.rooms.findIndex(room => room.id === this.selectedRoomId);
			if (index !== -1) {
				this.rooms[index].name = this.roomForm.rname;
				// this.offices[index].address = this.officeForm.oaddress; // Если обновляется адрес
			}
	
			alert('Помещение обновлено успешно');
			this.showRoomForm = false; // Закрываем форму после успешного обновления
			}
		});
		} else {
		alert('Пожалуйста, заполните все поля');
		}
	}
	

constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.user) {
		this.isreg = 1;
	}
	else
	{
		console.log('not reged');
	}
};

  
filterOffices() {
	if (this.role === 'ADMIN') {
	  this.filteredOffices = [...this.offices]; // Все офисы для администратора
	} else {
	  // Логика фильтрации доступных офисов для сотрудника
	  this.filteredOffices = this.offices.filter((office) => {
		// Добавьте условие фильтрации, если оно известно, например:
		return this.groups.some((group) => group.office === office.name);
	  });
	}
  }
  

// officeslist() {
// 	let params = new HttpParams().set('type', 'offices');
// 	this.http.get(this.baseUrl, { params }).subscribe((data) => {
// 	  this.offices = [];
// 	  (Object.keys(data)).forEach((key, index) => {
// 		this.offices.push({
// 		  id: Object.values(data)[index]["id"],
// 		  name: Object.values(data)[index]["name"],
// 		});
// 	  });
  
// 	  // Фильтрация офисов
// 	  this.filterOffices();
// 	});
//   }
  
//   filterOffices() {
// 	if (this.role === 'ADMIN') {
// 	  this.filteredOffices = [...this.offices]; // Все офисы для администратора
// 	} else {
// 	  // Логика фильтрации доступных офисов для сотрудника
// 	  this.filteredOffices = this.offices.filter((office) => {
// 		// Добавьте условие фильтрации, если оно известно, например:
// 		return this.groups.some((group) => group.office === office.name);
// 	  });
// 	}
//   }

  
addRoom() {
    if (this.roomForm.rname != "") {
      var room_json = JSON.stringify({room_name: this.roomForm.rname});
      this.http
        .post(this.baseUrl + "/api/rooms", room_json, {
          headers: {
            Authorization: `Bearer ${this.authService.user.token}`,
          },
        })
        .subscribe((data) => {
          Object.keys(data).forEach((key, index) => {
            if (Object.values(data)[0] != "") {
              this.rooms.push({
                id: Object.values(data)[0],
                name: this.roomForm.rname,
                office: "",
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
		.set('type','delroom')
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
}
