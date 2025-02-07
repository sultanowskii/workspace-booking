import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private _data: any | null = null; // Внутреннее хранилище данных

  get data(): any | null {
    try {
      console.log(localStorage.getItem("data"));
      return JSON.parse(localStorage.getItem("data") ?? "null");
    } catch (e) {
      console.error("Error parsing user data:", e);
      return null;
    }
  }

  set data(value: any | null) {
    if (value) {
      localStorage.setItem("data", JSON.stringify(value));
    } else {
      localStorage.removeItem("data");
    }
  }
}
