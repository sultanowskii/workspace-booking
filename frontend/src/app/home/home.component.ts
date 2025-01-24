import { Component, inject} from "@angular/core";
import { HttpClient, HttpClientModule} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { AuthService } from "../services/auth.service";


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
    type: "",
  };

  loginForma: any = {
    logina: "",
    passworda: "",
  };
  isreg = 0;
  doauth = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    public authService: AuthService
  ) {
    if (authService.user) {
        this.isreg = 1;
      } else {
        console.log("not reged");
      }
    };
  
    reg() {
      if (
        this.loginForm.login != "" &&
        this.loginForm.password != "" &&
        this.loginForm.password == this.loginForm.password2
      ) {
        this.http
          .post(this.baseUrl + "/auth/signup", {
            username: this.loginForm.login,
            password: this.loginForm.password,
            role: this.loginForm.type,
          })
          .subscribe((data: any) => {
            this.authService.user = data;
            console.log("User created:", this.authService.user);
            if (this.loginForm.type === "ADMIN") {
              this.http
                .post(this.baseUrl + `/api/employees/${data.id}/grant-admin`, {})
                .subscribe(
                  () => console.log("Admin privileges granted to user:", data.id),
                  (error) => console.error("Error granting admin privileges:", error)
                );
            }
    
            this.doauth = 1;
            location.reload();
          });
      } else {
        alert("Ошибка заполнения формы");
      }
    }
    

  signIn() {
    this.http
      .post(this.baseUrl + "/auth/signin", {
        username: this.loginForma.logina,
        password: this.loginForma.passworda,
      })
      .subscribe((data: any) => {
        this.authService.user = data;
        console.log(data);
        location.reload();
      });
  }

  signOut() {
    this.authService.user.signOut().then(
      function () {
        console.log("Signed Out");
        window.location.reload();
      },
      function (error: any) {
        console.error("Sign Out Error", error);
      }
    );
  }
}
