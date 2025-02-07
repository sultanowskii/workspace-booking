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
  return this.authService.data?.user.role;
}
offices: Array<{id: number; name: string; address: string  } > = [];
rooms: Array<{id: number; name: string; office: string; }> = [];
groups: Array<{id: number; name: string; office: string;} > = [];
users: Array<{id: number; name: string; group: string; fullname: string; } > = [];
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
addUserForm:
any = {
grid: 0,
}
addGroupForm:
any = {
grof: 0,
}
addRoomForm:
any = {
roof: 0,
}


constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
	if (authService.data) {
		this.isreg = 1;
		this.officesList();
		this.groupsList();
		this.usersList();
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
      data.forEach((office: any) => {
        this.offices.push({
          id: office["id"],
          name: office["name"],
          address: office["address"]
        });
      });
    });
    this.filterOffices();
}

filterOffices() {
	if (this.role === 'ADMIN') {
	this.filteredOffices = [...this.offices];
	} else {
	this.filteredOffices = this.offices.filter((office) => {
		return this.groups.some((group) => group.office === office.name);
	});
	}
}

groupsList() {
    this.http
      .get(this.baseUrl + `/api/employeeGroups`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
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


usersList() {
  this.http
    .get(this.baseUrl + `/api/employees`, {
      headers: {
        Authorization: `Bearer ${this.authService.data.token}`,
      },
    })
    .subscribe((data: any) => {
      this.users.push({
        id: data["id"],
        name: data["username"],
        group: data["employeeGroupId"],
        fullname: data["fullName"],
      });
    });
}

addUserToOffice() {
  console.log(this.addUserForm.grid + " / " + this.selectedUser);
  if (this.addUserForm.grid != "") {
    var adduser_json = JSON.stringify({
      group: this.addUserForm.grid,
      add_user: this.selectedUser,
    });
    this.http
      .post(this.baseUrl + `/api/employees`, adduser_json, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
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

addGroupToOffice() {
  console.log(this.addGroupForm.ofid + " / " + this.selectedGroup);
  if (this.addGroupForm.ofid != "") {
    this.http
      .post(
        this.baseUrl +
          `/api/offices/${this.addGroupForm.ofid}/employeeGroups/${this.selectedGroup}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
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

addGroup() {
  if (this.groupForm.grname != "") {
    var grp_json = JSON.stringify({group_name: this.groupForm.grname});
    this.http
      .post(this.baseUrl + `/api/employeeGroups`, grp_json, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
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
  let params = new HttpParams().set("id", id);
  this.http
    .delete(this.baseUrl + `/api/employeeGroups`, {params})
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