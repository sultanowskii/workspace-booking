import { Component} from "@angular/core";
import { HttpClient, HttpClientModule} from "@angular/common/http";
import { CommonModule } from '@angular/common';
import { environment } from '../../environments/environment';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { getAuth,createUserWithEmailAndPassword, signInWithEmailAndPassword, onAuthStateChanged } from "firebase/auth";


@Component({
    selector: "home-app",
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    standalone: true,
    imports: [FormsModule,HttpClientModule,CommonModule],
})


export class HomeComponent {
  private baseUrl = environment.baseUrl;
  loginForm:
  any = {
    user: '',
    login: '',
    password: '',
    password2: '',
    type: ''
  }

  loginForma:
  any = {
    logina: '',
    passworda: ''
  }
  isreg = 0;
  doauth=1;

  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient) {
    const auth = getAuth();
  // проверяем авторизован ли пользователь
    onAuthStateChanged(auth, (user) => {
    if (user) {
      const uid = user.uid;
      //console.log(user.email);
      this.isreg = 1;
    }
        else
        {
          console.log('not reged');
        }
    });
  }

  reg()
  {
    //console.log(this.loginForm);
    if (
    this.loginForm.user!="" &&
      this.loginForm.login!="" &&
      this.loginForm.password!="" &&
      this.loginForm.password==this.loginForm.password2
    )
    {
    const auth = getAuth();
    createUserWithEmailAndPassword(auth, this.loginForm.login, this.loginForm.password).then((userCredential) =>
    {
      const user = userCredential.user;
      const uid = user.uid;
      console.log('User created:', user);
    var reg_json = JSON.stringify({user: this.loginForm.user, login: this.loginForm.login, status: this.loginForm.type});
  this.http.post(this.baseUrl, reg_json).subscribe(  data => {
    console.log(data);
    this.doauth=1;
  });
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      console.log(errorCode + ": " + errorMessage);
      alert(errorMessage);
    });
    }
    else  alert("Ошибка заполнения формы");
  }


  SignIn(){
    const auth = getAuth();
  signInWithEmailAndPassword(auth, this.loginForma.logina, this.loginForma.passworda)
  .then((userCredential) => {
    const user = userCredential.user;
    if (user) {
    const uid = user.uid;
    console.log(uid);
  }
  })
  .catch((error) => {
    const errorCode = error.code;
    const errorMessage = error.message;
    //alert(errorMessage);
    console.log(errorMessage);
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
}
