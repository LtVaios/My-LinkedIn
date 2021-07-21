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
  currentuser: User
  returnUrl: string;
  usercondition: boolean
  passcondition: boolean
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
              private router: Router) {
    this.usercondition = false;
    this.passcondition = false;
  }

  ngOnInit(): void {
    //this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    //this.authenticationService.logout();
  }

  onSubmit(): void {
    this.loading=true;
    //the function's code will only be executed after the subscribe get the results from the observable that came from the database
    this.service.getUser(this.loginForm.value.email).subscribe(user => {
      this.currentuser = user;
      if (this.currentuser.password === this.loginForm.value.pass)
        this.router.navigate(["http://localhost:4200/home"]);
      else
        this.passcondition = true
      this.loginForm.reset();
      this.usercondition = false;
      this.passcondition = false;
      if(this.currentuser.username!="_EMPTY_") {
        this.shared_service.changeUser(this.currentuser.id);
        if (this.currentuser.admin == true)
          this.service.adminloggedin();
        else
          this.service.userloggedin();
      }
      else
        this.usercondition = true
    }, error => this.loginForm.reset());
  }
}
