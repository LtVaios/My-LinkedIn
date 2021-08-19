import { Component, OnInit } from '@angular/core';
import {RegisterService} from "./register.service";
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../model/user";
import {AuthenticationService} from "../authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  usercondition: boolean
  passcondition: boolean
  requiredcondition: boolean
  RegisterForm = this.formBuilder.group({
    email: '',
    fname: '',
    lname: '',
    pass: '',
    confirmpass:'',
    phone:'',
    picture: '',
  });
  selectedFile: File;

  constructor(private service: RegisterService,private formBuilder: FormBuilder,private router: Router,private authenticationService: AuthenticationService) {
    this.usercondition=false;
    this.passcondition=false;
    this.requiredcondition=false;
    this.user=new User();
  }

  ngOnInit(): void {
    this.authenticationService.logout();
  }


  async onSubmit() {
    this.usercondition=false;
    this.requiredcondition = false;
    this.passcondition=false;
    if (this.RegisterForm.value.pass != this.RegisterForm.value.confirmpass){
      this.passcondition = true;
      return;
    }
    if(this.RegisterForm.value.email==="" || this.RegisterForm.value.email===null
      || this.RegisterForm.value.fname==="" || this.RegisterForm.value.fname===null
      || this.RegisterForm.value.lname===""|| this.RegisterForm.value.lname===null
      || this.RegisterForm.value.pass===""|| this.RegisterForm.value.pass===null
      || this.RegisterForm.value.phone===""|| this.RegisterForm.value.phone===null) {
      this.requiredcondition = true;
      this.RegisterForm.reset();
    }
    //await this.service.getUser(this.RegisterForm.value.email).toPromise().then(user=> this.user=user);

      this.service.userregister(this.RegisterForm.value.fname, this.RegisterForm.value.lname,
        this.RegisterForm.value.email, this.RegisterForm.value.pass, this.RegisterForm.value.phone).subscribe(
          user => {
            this.user = user;
            this.service.postImage(this.selectedFile, this.user.id).subscribe((response) => console.log(response));
            }, error => console.log(error));
      window.alert("You signed up successfully!")
      this.router.navigate(['../login']);

  }

  public onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }
}
