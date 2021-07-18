import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../shared.service";
import {InfoService} from "../info/info.service";
import {User} from "../model/user";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  currentUser: string
  saved: boolean
  user: User

  InfoForm = this.formBuilder.group({
    workexp_text: '',
    education_text: '',
    skills_text: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: InfoService) {
  }

  async ngOnInit(): Promise<User> {
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    console.log(this.service.getUser(this.currentUser));
    console.log(this.currentUser);
    this.saved = false;
    this.user  = await this.service.getUser(this.currentUser).toPromise();
    console.log("USER "+this.user);
    this.InfoForm.value.workexp_text = this.user.work_experience;
    return this.user;
  }

  onSubmit(): void {
    console.log('on submit'+this.InfoForm.value.workexp_text);
    this.service.changeWorkexp(this.currentUser, this.InfoForm.value.workexp_text).subscribe(data=>this.InfoForm.value.workexp_text=data);
    this.service.changeEducation(this.currentUser, this.InfoForm.value.education_text).subscribe(data=>this.InfoForm.value.workexp_text=data);
    this.service.changeSkills(this.currentUser, this.InfoForm.value.skills_text).subscribe(data=>this.InfoForm.value.workexp_text=data);
    this.saved = true;
  }
}
