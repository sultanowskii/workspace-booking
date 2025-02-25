import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { switchMap } from "rxjs";
import { UserService } from "../services/user.service";

@Component({
    selector: "users-app",
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss'],
    standalone: true,
    imports: [FormsModule, HttpClientModule, CommonModule],
})


export class UsersComponent {
    private baseUrl = environment.baseUrl;
    selectedUser = 0;
    selectedGroups: { [username: string]: number } = {};
    selectedGroupId = 0;
    selectedRoom = 0;
    showOfficeForm: boolean = false;
    isreg = 0;

    get role(): any {
        return this.authService.data?.user.role;
    }
    currentUserPage = 1;
    usersPerPage = 5;
    showGroupForm: boolean = false;
    officeForm:
        any = {
            name: '',
            address: '',
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

    constructor(protected userService: UserService, protected readonly authService: AuthService) {
        if (authService.data) {
            this.isreg = 1;
            this.userService.officesList();
            this.userService.groupsList();
            this.userService.usersList();
        }
        else {
            console.log('not reged');
        }
    };

    
    get totalUserPages(): number {
        return Math.ceil(this.userService.users.length / this.usersPerPage);
    }

    get paginatedUsers() {
        const startIndex = (this.currentUserPage - 1) * this.usersPerPage;
        return this.userService.users.slice(startIndex, startIndex + this.usersPerPage);
    }

    prevUserPage() {
        if (this.currentUserPage > 1) {
            this.currentUserPage--;
        }
    }

    nextUserPage() {
        if (this.currentUserPage < this.totalUserPages) {
            this.currentUserPage++;
        }
    }
    
}