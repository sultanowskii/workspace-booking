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
  selectedGroupId = 0;
  selectedRoom = 0;
  showOfficeForm: boolean = false;
  isreg = 0;

  get role(): any {
    return this.authService.data?.user.role;
  }
  offices: Array<{ id: number; name: string; address: string; employeeGroups: { if: number; name: string } }> = [];
  rooms: Array<{ id: number; name: string; office: string }> = [];
  groups: Array<{
    id: number; name: string; allowedOffices: {
      id: number,
      name: string,
      address: string;
    }[]
  }> = [];
  users: Array<{ id: number; group: string; fullname: string; }> = [];
  filteredOffices: Array<{ id: number; name: string }> = [];
  selectedOffices: { [groupId: number]: number } = {};
  currentPage = 1;
  itemsPerPage = 5;
  currentUserPage = 1;
  usersPerPage = 5;
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
  addRoomForm:
    any = {
      roof: 0,
    }


  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
    if (authService.data) {
      this.isreg = 1;
      this.officesList();
      this.groupsList();
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
            address: office["address"],
            employeeGroups: office["employeeGroups"]
          });
        })
      },
        (error: any) => {
          console.error("Error fetching offices:", error);
          alert("Ошибка при получении данных");
        }
      );
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
            allowedOffices: group["allowedOffices"]
          } as any);
        });
      });
  };

  addGroupToOffice(groupId: number, selectedOffice: number) {
    this.http
      .post(
        this.baseUrl +
        `/api/offices/${selectedOffice}/employeeGroups/${groupId}`,
        { officeId: selectedOffice, employeeGroupId: groupId },
        {
          headers: new HttpHeaders({
            Authorization: `Bearer ${this.authService.data.token}`,
          }),
        }
      )
      .subscribe(
        (data) => {
          console.log(data);
          alert("Группа прикреплена");
          this.groupForm.office = selectedOffice;
          this.groupsList();
        },
        (error) => {
          console.log(error);
        }
      );
  }

  delGroupFromOffice(groupId: number, selectedOffice: number) {
    this.http
      .delete(
        this.baseUrl +
        `/api/offices/${selectedOffice}/employeeGroups/${groupId}`,
        {
          headers: new HttpHeaders({
            Authorization: `Bearer ${this.authService.data.token}`,
          }),
          params: { officeId: selectedOffice, employeeGroupId: groupId }
        },
      )
      .subscribe(
        (data) => {
          alert("Группа откреплена");
          this.groupsList();
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

  getOfficeName(officeId: number): string {
    const office = this.offices.find(o => o.id === officeId);
    return office ? office.name : 'Неизвестный офис';
  }
  getOfficeNames(offices: any[]): string {
    return offices.map(office => office.name).join(', ');
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
        this.selectedGroupId = 0;
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


  get paginatedGroups() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.groups.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages() {
    return Math.ceil(this.groups.length / this.itemsPerPage);
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