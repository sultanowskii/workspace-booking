import { Component, inject, model, signal, importProvidersFrom} from "@angular/core";

import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';


import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";

@Component({
selector: "groups-app",
templateUrl: './groups.component.html',
styleUrls: ['./groups.component.scss'],
standalone: true,
imports: [FormsModule,HttpClientModule,CommonModule],
})


export class GroupsComponent {
private baseUrl = environment.baseUrl;
selectedUser = 0;
selectedGroup = 0;
selectedRoom = 0;
showOfficeForm: boolean = false;
isreg = 0;

get role(): any {
  return this.authService.user.role;
}
offices: Array<{id: number; name: string; address: string  } > = [];
rooms: Array<{id: number; name: string; office: string; }> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
users: Array<{id: number; name: string; group: string; groupname: string; } > = [];
filteredOffices: Array<{ id: number; name: string }> = [];
selectedOfficeId: number | null = null; // ID редактируемого офиса

officeForm:
any = {
oname: '',
oaddress: '',
}

roomForm:
any = {
rname: '',
}

groupForm:
any = {
grname: '',
}
adduserForm:
any = {
grid: 0,
}
addgroupForm:
any = {
grof: 0,
}
addroomForm:
any = {
roof: 0,
}


constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.user) {
		this.isreg = 1;
		this.officeslist();
		this.groupslist();
		this.userslist();
	}
	else
	{
		console.log('not reged');
	}
};


officeslist() {
    this.http
      .get(`${this.baseUrl}/api/offices`, {
        headers: {
          Authorization: `Bearer ${this.authService.user.token}`,
        },
      })
      .subscribe((data: any) => {
        data.forEach((office: any) => {
          this.offices.push({
            id: office["id"],
            name: office["name"],
			address: office["address"]
          });
        });
      });
  }

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


groupslist() {
    this.http
      .get(this.baseUrl + "/api/employeeGroups", {
        headers: {
          Authorization: `Bearer ${this.authService.user.token}`,
        },
      })
      .subscribe((data: any) => {
        data.forEach((group: any) => {
          this.groups.push({
            id: group["id"],
            name: group["name"],
          } as any);
        });
      });
  }


  userslist() {
    this.http
      .get(this.baseUrl + "/api/employees", {
        headers: {
          Authorization: `Bearer ${this.authService.user.token}`,
        },
      })
      .subscribe((data: any) => {
        this.users.push({
          id: data["id"],
          name: data["username"],
          group: data["employeeGroupId"],
          groupname: "fff",
        });
      });
  }

  addgroup() {
    console.log(this.addgroupForm.ofid + " / " + this.selectedGroup);
    if (this.addgroupForm.ofid != "") {
      this.http
        .post(
          this.baseUrl +
            `/api/offices/${this.addgroupForm.ofid}/employeeGroups/${this.selectedGroup}`,
          {},
          {
            headers: {
              Authorization: `Bearer ${this.authService.user.token}`,
            },
          }
        )
        .subscribe(
          (data) => {
            if (data != null) {
              Object.keys(data).forEach((key, index) => {
                if (Object.values(data)[0] == "ok") {
                  alert("Группа прикреплена");
                }
              });
            }
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }

  addUser() {
    console.log(this.adduserForm.grid + " / " + this.selectedUser);
    if (this.adduserForm.grid != "") {
      var adduser_json = JSON.stringify({
        group: this.adduserForm.grid,
        add_user: this.selectedUser,
      });
      this.http
        .post(this.baseUrl + "/api/employees", adduser_json, {
          headers: {
            Authorization: `Bearer ${this.authService.user.token}`,
          },
        })
        .subscribe(
          (data) => {
            if (data != null) {
              Object.keys(data).forEach((key, index) => {
                if (Object.values(data)[0] == "ok") {
                  alert("Пользователь прикреплен");
                }
              });
            }
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }

  addroom() {
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


  addGroup() {
    if (this.groupForm.grname != "") {
      var grp_json = JSON.stringify({group_name: this.groupForm.grname});
      this.http
        .post(this.baseUrl + "/api/groups", grp_json, {
          headers: {
            Authorization: `Bearer ${this.authService.user.token}`,
          },
        })
        .subscribe((data) => {
          Object.keys(data).forEach((key, index) => {
            if (Object.values(data)[0] != "") {
              this.groups.push({
                id: Object.values(data)[0],
                name: this.groupForm.grname,
                office: "",
              });
              alert("Группа успешно создана");
            }
          });
        });
    } else {
      alert("Введите название группы");
    }
  }

  delGroup(id: number) {
    let params = new HttpParams().set("type", "delgroup").set("id", id);
    this.http
      .delete(this.baseUrl + "/api/employeeGroups", {params})
      .subscribe((data) => {
        Object.keys(data).forEach((key, index) => {
          if (Object.values(data)[0] == "ok") {
            this.groups = this.groups.filter((group) => group.id !== id);
            alert("Группа удалена");
          }
        });
      });
    return false;
  }
}