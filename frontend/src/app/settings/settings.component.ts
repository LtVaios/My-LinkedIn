import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../shared.service";
import {AuthenticationService} from "../authentication.service";
import {User} from "../model/user";
import {SettingsService} from "../settings/settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent implements OnInit {
  passcondition: boolean
  currentUser: number
  user: User
  loading: boolean
  emailsaved: boolean
  passsaved:boolean
  passfilledCondition:boolean
  emailfilledCondition:boolean

  PassForm = this.formBuilder.group({
    new_pass: '',
    conf_pass: ''
  });

  EmailForm = this.formBuilder.group({
    new_email: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: SettingsService) {
  }

  async ngOnInit() {
    this.loading=false;
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    //await new Promise(f => setTimeout(f, 5000));
    this.passsaved = false;
    this.emailsaved=false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    // console.log("USER "+this.user.work_experience);
    this.loading=true;
  }

  PassSubmit(): void {
    this.passcondition=false;
    this.passsaved = false;
    this.passfilledCondition=false;
    if(this.PassForm.value.new_pass==="" || this.PassForm.value.new_pass===null)
    {
      this.passfilledCondition = true;
      this.PassForm.reset();
    }
    else {
      if (this.PassForm.value.new_pass !== this.PassForm.value.conf_pass) {
        this.passcondition = true;
        this.PassForm.reset();
      } else {
        this.user.password = this.PassForm.value.new_pass;
        this.service.updateUser(this.currentUser, this.user).subscribe(next => console.log("subscribed new pass"));
      }
      this.passsaved = true;
      this.PassForm.reset();
    }
  }

  EmailSubmit(): void {
    this.emailsaved = false;
    this.emailfilledCondition=false;
    if(this.EmailForm.value.new_email==="" || this.EmailForm.value.new_email===null)
    {
      this.emailfilledCondition = true;
      this.PassForm.reset();
    }
    else {
      this.user.username = this.EmailForm.value.new_email;
      this.service.updateUser(this.currentUser, this.user).subscribe(next => console.log("subscribed new email"));
      this.emailsaved = true;
      this.EmailForm.reset();
    }
  }
}
