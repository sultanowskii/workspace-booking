import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { RouterOutlet, ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams } from "@angular/common/http";
import { CommonModule } from "@angular/common";
import { AuthService } from './services/auth.service';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  isreg = 0;
  doauth = 1;
  title = 'Бронирование офисов';
  act = '';
  role = '';
  private baseUrl = environment.baseUrl;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private location: Location, public authService: AuthService) {
    if (this.authService.data) {
      this.isreg = 1;
      console.log('authService.data:', this.authService.data);
    }
    else {
      console.log('not reged app');
    }
  };

  signOut() {
    localStorage.removeItem("data");
    location.reload();
  }


  ngOnInit() {
    this.act = this.location.path();
  }

}