import { Injectable } from "@angular/core";
import { Component, inject, model, signal, importProvidersFrom } from "@angular/core";
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "./auth.service";
import { threadId } from "worker_threads";

@Injectable({ providedIn: 'root' })
export class UserService {
    private baseUrl = environment.baseUrl;
    offices: Array<{ id: number; name: string; address: string }> = [];
    groups: Array<{ id: number; name: string; office: string[]; }> = [];
    users: Array<{ id: number; group: string; username: string; }> = [];

    constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, public authService: AuthService) {
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
                        office: group["allowedOffices"],
                    } as any);
                });
            });
    };

    usersList() {
        if (this.authService.data.user.role === 'ADMIN') {
            this.http
                .get(this.baseUrl + `/api/employees`, {
                    headers: {
                        Authorization: `Bearer ${this.authService.data.token}`,
                    },
                })
                .subscribe((data: any) => {
                    this.users = [];
                    for (const key in data) {
                        this.users.push({
                            id: data[key].id,
                            group: data[key].employeeGroupId,
                            username: data[key].username,
                        });
                    }
                    this.users.sort((a, b) => a.id - b.id);
                });
        } else {
            this.http
                .get(this.baseUrl + `/api/employees`, {
                    headers: {
                        Authorization: `Bearer ${this.authService.data.token}`,
                    },
                })
                .subscribe((data: any) => {
                    this.users = [];
                    data = data.filter((emp: any) => emp.username === this.authService.data.user.username);
                    console.log(data);
                    this.authService.data.user.group = data;
                    for (const key in data) {
                        this.users.push({
                            id: data[key].id,
                            group: data[key].employeeGroupId,
                            username: data[key].username,
                        });
                    }
                    this.users.sort((a, b) => a.id - b.id);
                });
        }
    }

    addUserToGroup(userId: number, username: string, groupId: number) {
        this.http
            .put(this.baseUrl + `/api/employees/${userId}`, {
                fullName: username,
                employeeGroupId: groupId
            }, {
                headers: new HttpHeaders({
                    Authorization: `Bearer ${this.authService.data.token}`,
                })
            })
            .subscribe(
                (data) => {
                    console.log(data);
                    if (data != null) {
                        alert("Пользователь прикреплен");
                        this.usersList();
                    }
                },
                (error) => {
                    console.log(error);
                    if (error.status === 403) {
                        alert('Нет прав для выполнения этой операции.');
                    }
                }
            );
    }


    delUserFromGroup(userId: number, user: string, groupId: number) {
        this.http
            .put(this.baseUrl + `/api/employees/${userId}`, {
                fullName: user,
                employeeGroupId: null
            }, {
                headers: new HttpHeaders({
                    Authorization: `Bearer ${this.authService.data.token}`,
                })
            })
            .subscribe(
                (data) => {
                    console.log(data);
                    alert("Пользователь откреплен");
                    this.usersList();
                },
                (error) => {
                    console.log(error);
                }
            );
    }


    delEmployee(id: number) {
        this.http
            .delete(this.baseUrl + `/api/employees/${id}`, {
                headers: {
                    Authorization: `Bearer ${this.authService.data.token}`
                },
            })
            .subscribe((data) => {
                this.usersList();
                alert("Пользователь удален");
            });
        return false;
    }

}