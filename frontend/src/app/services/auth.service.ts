import {Injectable} from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  get user(): any {
    return JSON.parse(localStorage.getItem("user") ?? "null");
  }

  set user(value: any) {
    localStorage.setItem("user", JSON.stringify(value));
  }
}
