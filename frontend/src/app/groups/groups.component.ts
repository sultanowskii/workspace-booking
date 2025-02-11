import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Component({
  selector: "groups-app",
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss'],
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule],
})


export class GroupsComponent {
  private baseUrl = environment.baseUrl;
  selectedUser = 0;
  selectedGroup = 0;
  selectedGroupId: number | null = null;
  selectedRoom = 0;
  showOfficeForm: boolean = false;
  isreg = 0;

  get role(): any {
    return this.authService.data?.user.role;
  }
  offices: Array<{ id: number; name: string; address: string }> = [];
  rooms: Array<{ id: number; name: string; office: string; }> = [];
  groups: Array<{ id: number; name: string; office: string; }> = [];
  users: Array<{ id: number; name: string; group: string; fullname: string; }> = [];
  filteredOffices: Array<{ id: number; name: string }> = [];
  selectedOffice: number | null = null;

  showGroupForm: boolean = false;
  officeForm:
    any = {
      name: '',
      address: '',
    }

  roomForm:
    any = {
      name: '',
    }

  groupForm:
    any = {
      name: '',
      office: ''
    }
  addUserForm:
    any = {
      grname: '',
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
    else {
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
    if (this.role === 'ADMIN') {
      this.filteredOffices = [...this.offices];
    } else {
      this.filteredOffices = this.offices.filter((office) => {
        console.log("Groups from API:", this.groups);
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
        this.groups = [];
        data.forEach((group: any) => {
          this.groups.push({
            id: group["id"],
            name: group["name"],
            office: group["office"]
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
        this.users = [];

        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            const user = data[key];
            this.users.push({
              id: user.id,
              name: user.username,
              group: user.employeeGroupId,
              fullname: user.fullName,
            });
          }
        }
      });
  }

  addUserToOffice() {   ///нет такого запроса?
    if (this.addUserForm.grname != "") {
      const adduser = {
        name: this.addUserForm.grname
      };
      this.http
        .post(this.baseUrl + `/api/employeeGroups?${this.selectedUser}`, adduser, {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        })
        .subscribe(
          (data) => {
            console.log(data);
            if (data != null) {
              Object.keys(data).forEach((key, index) => {
                alert("Пользователь прикреплен");
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
          { officeId: this.addGroupForm.ofid, employeeGroupId: this.selectedGroup },
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
                alert("Группа прикреплена");
              });
            }
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }

  delGroupFromOffice() {
    console.log(this.addGroupForm.ofid + " / " + this.selectedGroup);
    this.http
      .delete(
        this.baseUrl +
        `/api/offices/${this.addGroupForm.ofid}/employeeGroups/${this.selectedGroup}`,
        {
          params: { officeId: this.addGroupForm.ofid, employeeGroupId: this.selectedGroup },
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        }
      )
      .subscribe(
        (data) => {
          if (data != null) {
            Object.keys(data).forEach((key, index) => {
              alert("Группа откреплена");
            });
          }
        },
        (error) => {
          console.log(error);
        }
      );
  }

  addGroup() {
    if (this.groupForm.name != "") {
      const grp = { name: this.groupForm.name };
      this.http
        .post(this.baseUrl + `/api/employeeGroups`, grp, {
          headers: {
            Authorization: `Bearer ${this.authService.data.token}`,
          },
        })
        .subscribe((data) => {
          this.groupsList();
          alert("Группа успешно создана");
          this.groupForm = {};
        });

    } else {
      alert("Введите название группы");
    }
  }

  openEditGroupForm(group: { id: number, name: string }) {
    this.groupForm.name = group.name;
    this.showGroupForm = true;
    this.selectedGroupId = group.id;
  }

  openGroupForm() {
    this.showGroupForm = true;
  }

  closeGroupForm() {
    this.showGroupForm = false;
    this.groupForm.name = '';
    this.groupForm.office = '';
  }


  editGroup() {
    if (this.groupForm.name) {
      const grp = { name: this.groupForm.name };

      this.http.put(this.baseUrl + `/api/employeeGroups/${this.selectedGroupId}`, grp, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`,
        }
      }).subscribe((data) => {
        this.groupsList();
        alert('Группа обновлена успешно');
        this.showGroupForm = false;
        this.selectedGroupId = null;
        this.groupForm = {};
      }
      );
    } else {
      alert('Пожалуйста, заполните все поля');
    }
  }

  delGroup(id: number) {
    this.http
      .delete(this.baseUrl + `/api/employeeGroups/${id}`, {
        headers: {
          Authorization: `Bearer ${this.authService.data.token}`
        },
      })
      .subscribe((data) => {
        this.groupsList();
        alert("Группа удалена");
      });
    return false;
  }
}