import { Component,  OnInit  } from '@angular/core';
import { Location } from '@angular/common';
import { RouterOutlet,ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";
import { AuthService } from './services/auth.service';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  isreg = 0;
  title = 'Бронирование офисов';
  act = '';
  role = '';
  private baseUrl = environment.baseUrl;
constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private location: Location, public authService: AuthService) {
    console.log('AuthService.user:', this.authService.user);

  if (this.authService.user) {
    this.isreg = 1;
    console.log('AuthService.user:', this.authService.user);
    }
  else
      {
        console.log('not reged app');
      }
  };

    signOut(){
        if (typeof window !== 'undefined') {
          localStorage.removeItem("user");
          console.log('Signed Out');
          window.location.reload();
        }
  }

  ngOnInit() {
    this.act = this.location.path();
  }

}