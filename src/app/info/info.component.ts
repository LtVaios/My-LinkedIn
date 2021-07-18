import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../shared.service";
import {InfoService} from "../info/info.service";
import {User} from "../model/user";
import {UserService} from "../user/user.service";
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

  InfoForm = this.formBuilder.group({
    work_experience: '',
    education: '',
    skills: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: InfoService) {
  }

  async ngOnInit() {
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    console.log(this.service.getUser(this.currentUser));
    console.log(this.currentUser);
    this.saved = false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    console.log("USER "+this.user.work_experience);

    this.InfoForm.patchValue(this.user);
    this.service.getUser(this.currentUser)
      .pipe(tap(user => this.InfoForm.patchValue(user)));
  }

  onSubmit(): void {
    console.log('on submit'+this.InfoForm.value.workexp_text);
    if (this.InfoForm.value.work_experience != '')
      this.service.changeWorkexp(this.currentUser, this.InfoForm.value.work_experience).subscribe();
    if (this.InfoForm.value.education != '')
      this.service.changeEducation(this.currentUser, this.InfoForm.value.education).subscribe();
    if (this.InfoForm.value.skills != '')
      this.service.changeSkills(this.currentUser, this.InfoForm.value.skills).subscribe();
    this.saved = true;
  }

}
