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
  saved: boolean

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
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    //await new Promise(f => setTimeout(f, 5000));
    // console.log(this.service.getUser(this.currentUser));
    console.log(this.currentUser);
    this.saved = false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    console.log(this.user.username);
    // console.log("USER "+this.user.work_experience);
    this.loading=true;
  }

  PassSubmit(): void {
    this.passcondition=false;
    if(this.PassForm.value.new_pass !== this.PassForm.value.conf_pass){
      this.passcondition = true;
      this.PassForm.reset();
    }
    else {
      this.user.password = this.PassForm.value.new_pass;
      this.service.updateUser(this.currentUser, this.user).subscribe(next=>console.log("subsribed"));
    }
    this.saved = true;
  }

  EmailSubmit(): void {
    this.user.username = this.EmailForm.value.new_email;
    console.log(this.user.username);
    console.log(this.currentUser);
    this.service.updateUser(this.currentUser, this.user).subscribe();
    this.saved = true;
  }
}
