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
  currentUser: string

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

  ngOnInit(): void {
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
  }

  PassSubmit(): void {
    this.passcondition=false;
    if(this.PassForm.value.new_pass !== this.PassForm.value.conf_pass){
      this.passcondition = true;
      this.PassForm.reset();
    }
    else {
      this.service.changePass(this.currentUser, this.PassForm.value.new_pass);
    }

  }

  EmailSubmit(): void {
      this.service.changeEmail(this.currentUser, this.EmailForm.value.new_email).subscribe(next=>console.log("subsribed"));
  }
}
