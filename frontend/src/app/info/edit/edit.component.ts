import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user";
import {SharedService} from "../../shared.service";
import {FormBuilder} from "@angular/forms";
import {EditService} from "../edit/edit.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  loading:boolean
  currentUser: number
  saved: boolean
  user: User

  InfoForm = this.formBuilder.group({
    work_experience: '',
    work_experience_public: false,
    education: '',
    education_public: false,
    skills: '',
    skills_public: false,
    job_pos: '',
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: EditService,
              private router: Router) { this.user=new User(); }

  async ngOnInit() {
    //await new Promise(f => setTimeout(f, 5000));
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    // console.log(this.service.getUser(this.currentUser));
    //console.log(this.currentUser);
    this.saved = false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    this.InfoForm.patchValue(this.user);
  }

  onSubmit(): void {
    // console.log('on submit'+this.InfoForm.value.work_experience);

    this.user.skills = this.InfoForm.value.skills;
    this.user.skills_public = this.InfoForm.value.skills_public;

    this.user.education = this.InfoForm.value.education;
    this.user.education_public = this.InfoForm.value.education_public;

    this.user.work_experience = this.InfoForm.value.work_experience;
    this.user.work_experience_public = this.InfoForm.value.work_experience_public;
    this.user.job_pos=this.InfoForm.value.job_pos;
    this.service.updateUser(this.currentUser, this.user).subscribe();
    this.saved = true;
    this.router.navigate(['../info'])
  }

}
