import { Component,  OnInit  } from '@angular/core';
import { Location } from '@angular/common';
import { RouterOutlet,ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpClientModule, HttpParams} from "@angular/common/http";

import { initializeApp } from "firebase/app";

// подключаемся к серверу авторизации

const firebaseConfig = {
  apiKey: "AIzaSyCIZSgMNibuHqI3LxzDLHh9GO_2wH_b5KE",
  authDomain: "myoffice-4ce89.firebaseapp.com",
  projectId: "myoffice-4ce89",
  storageBucket: "myoffice-4ce89.firebasestorage.app",
  messagingSenderId: "304569898811",
  appId: "1:304569898811:web:d521dbd88ba2d6a2d2891e",
  measurementId: "G-EPE3JEM4M7"
};

const app = initializeApp(firebaseConfig);
console.log("Firebase initialized", app);

import { getAuth,createUserWithEmailAndPassword, signInWithEmailAndPassword, onAuthStateChanged } from "firebase/auth";


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
  status = '';
  status_get: Array<{status: string; } > = [];
   constructor(private route: ActivatedRoute, private router: Router,private location: Location, private http: HttpClient) {
    const auth = getAuth();
    onAuthStateChanged(auth, (user) => {
		if (user) {
			const uid = user.uid;
			console.log(user.email);
			this.isreg = 1;
			//console.log(user.email);
			if (user.email!=null)
			{
				this.GetStatus(user.email);
			}
		}
        else
        {
          console.log('not reged app');
        }
    });
  }


  SignOut(){
const auth = getAuth();
   auth.signOut().then(function() {
    console.log('Signed Out');
    window.location.reload();
    },
    function(error) {
    console.error('Sign Out Error', error);
  });
}

ngOnInit() {
  this.act = this.location.path();
     //console.log(this.location.path());
  }

  GetStatus(user: string) {
    let params = new HttpParams()
        .set('type', 'status')
        .set('user', user);
    this.http.get('', {params}).subscribe(data => {
      console.log('API response:', data);
      (Object.keys(data)).forEach((key, index) => {
        this.status = Object.values(data)[0];
      });
    });
  }
}