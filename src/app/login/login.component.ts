import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {LoginService} from './login.service';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import {SharedService} from "../shared.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  currentuser: User
  returnUrl: string;
  loading = false;

  loginForm = this.formBuilder.group({
    email: '',
    pass: ''
  });

  constructor(private service: LoginService,
              private shared_service: SharedService,
              private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    //console.log(this.returnUrl)
    if(this.returnUrl==="/login" || this.returnUrl==="/")
      this.returnUrl="/home"
    this.authenticationService.logout();
  }

  async onSubmit() {
    await this.authenticationService.login(this.loginForm.value.email, this.loginForm.value.pass)
      .toPromise().then(
        async response => {
          localStorage.setItem('token', <string>response.headers.get('Authorization'));
          await this.service.getUser(this.loginForm.value.email).toPromise().then(user => {
            this.currentuser = user
            if(user.admin==true)
              this.adminloggedin();
            else
              this.userloggedin();
          });

        }
      );
    localStorage.setItem('currentuser', String(this.currentuser.id))
  }

  userloggedin():void {
    this.router.navigate(['../home']);
  }

  adminloggedin(): void {
    this.router.navigate(['../admin']);
  }

}
