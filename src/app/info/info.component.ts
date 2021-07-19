import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {InfoService} from "../info/info.service";
import {User} from "../model/user";

import {tap} from "rxjs/operators";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  currentUser: string
  saved: boolean
  user: User
  work_experience: String
  education: String
  skills: String

  constructor(private sharedService: SharedService,
              private service: InfoService) {
  }

  async ngOnInit() {
    this.sharedService.curr_user.subscribe(user => this.currentUser = user);
    // console.log(this.service.getUser(this.currentUser));
    // console.log(this.currentUser);
    this.saved = false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    this.work_experience = this.user.work_experience;
    this.education = this.user.education;
    this.skills = this.user.skills;
    // console.log("USER " + this.user.work_experience);
  }

}
