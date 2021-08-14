import { Component, OnInit } from '@angular/core';
import {RegisterService} from "./register.service";
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../model/user";

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

  constructor(private service: RegisterService,private formBuilder: FormBuilder,private router: Router) {
    this.usercondition=false;
    this.passcondition=false;
    this.requiredcondition=false;
    this.user=new User();
  }

  ngOnInit(): void {
  }


  onSubmit():void {
    this.usercondition=false;
    this.requiredcondition = false;
    this.passcondition=false;
    if (this.RegisterForm.value.pass != this.RegisterForm.value.confirmpass){
      this.passcondition = true;
      return;
    }
    this.service.getUser(this.RegisterForm.value.email).subscribe(user=> {
      this.user=user;
      //console.log("form email:" + this.RegisterForm.value.email)
      if(this.RegisterForm.value.email==="" || this.RegisterForm.value.email===null
        || this.RegisterForm.value.fname==="" || this.RegisterForm.value.fname===null
        || this.RegisterForm.value.lname===""|| this.RegisterForm.value.lname===null
        || this.RegisterForm.value.pass===""|| this.RegisterForm.value.pass===null
        || this.RegisterForm.value.phone===""|| this.RegisterForm.value.phone===null)
        {
        this.requiredcondition = true;
        this.RegisterForm.reset();
      }
      else if(this.user.username!="_EMPTY_") {
        this.usercondition = true;
        this.RegisterForm.reset();
      }
      else {
        this.service.userregister(this.RegisterForm.value.fname, this.RegisterForm.value.lname, this.RegisterForm.value.email, this.RegisterForm.value.pass, this.RegisterForm.value.phone).subscribe(user => this.user = user, error => console.log(error));
        window.alert("You signed up successfully!")
        this.router.navigate(['../login']);
      }
    });
  }

  // onUpload() {
  //   // console.log(this.selectedFile);
  //   //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
  //   const uploadImageData = new FormData();
  //   uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
  //   //Make a call to the Spring Boot Application to save the image
  //   this.httpClient.post('http://localhost:8080/image/upload', uploadImageData, { observe: 'response' })
  //     .subscribe((response) => {
  //         if (response.status === 200) {
  //           this.message = 'Image uploaded successfully';
  //         } else {
  //           this.message = 'Image not uploaded successfully';
  //         }
  //       }
  //     );
  // }

  public onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }
}
