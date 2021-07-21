import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {InfoService} from "../info/info.service";
import {User} from "../model/user";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  currentUser: number
  user: User
  work_experience: string
  education: string
  skills: string

  constructor(private sharedService: SharedService,
              private service: InfoService) {
  }

  async ngOnInit() {
    this.sharedService.curr_user.subscribe(user => this.currentUser = user);

    // console.log(this.service.getUser(this.currentUser));
    // console.log(this.currentUser);
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    this.work_experience = this.user.work_experience;
    this.education = this.user.education;
    this.skills = this.user.skills;
    // console.log("USER " + this.user.work_experience);
  }

}
