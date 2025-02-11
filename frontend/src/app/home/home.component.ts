import { Component, inject } from "@angular/core";
import { HttpClient, HttpClientModule, HttpErrorResponse } from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { catchError, of, switchMap } from "rxjs";

@Component({
  selector: "home-app",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule],
})
export class HomeComponent {
  private baseUrl = environment.baseUrl;
  loginForm: any = {
    login: "",
    password: "",
    password2: "",
    role: "",
  };

  loginFormAuth: any = {
    loginAuth: "",
    passwordAuth: "",
  };
  isreg = 0;
  doauth = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    public authService: AuthService
  ) {
    if (this.authService.data && this.authService.data.user.username) {
      this.isreg = 1;
    }
    else {
      console.log("User not registered");
    }
  }

  reg() {
    if (
      this.loginForm.login != "" &&
      this.loginForm.password != "" &&
      this.loginForm.password == this.loginForm.password2 &&
      this.loginForm.role != ""
    ) {
      console.log(this.loginForm.role)
      this.http
        .post(this.baseUrl + `/auth/signup`, {
          username: this.loginForm.login,
          password: this.loginForm.password,
          role: this.loginForm.role
        })
        .pipe(
          switchMap((data: any) => {
            this.authService.data = data;
            console.log('User created:', this.authService.data);
            if (this.loginForm.role === 'ADMIN') {
              return this.http.post(this.baseUrl + `/api/employees/${data.user.id}/grant-admin`, {});
            }
            return of(void 0);
          }
          ),
          catchError((err: any) => {
            console.error("Error granting admin privileges:", err)
            return of(void 0)
          }
          )
        )
        .subscribe((data: any) => {
          console.log('Admin privileges granted to user:', data.user.id);
          this.authService.data.user.role = 'ADMIN';
          this.doauth = 1;
          location.reload();
        });
    } else {
      alert("Ошибка заполнения формы");
    }
  }

  signIn() {
    console.log("signin")
    this.http
      .post(this.baseUrl + `/auth/signin`, {
        username: this.loginFormAuth.loginAuth,
        password: this.loginFormAuth.passwordAuth,
      })
      .subscribe((data: any) => {
        this.authService.data = data;
        console.log(data);
        location.reload();
      });
  }

  signOut() {
    this.authService.data.signOut().then(
      () => {
        console.log("Signed Out");
        window.location.reload();
      },
      (error: any) => {
        console.error("Sign Out Error", error);
      }
    );
  }
}
