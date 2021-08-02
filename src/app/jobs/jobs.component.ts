import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../model/user";
import {JobsService} from "./jobs.service";
import {Job} from "../model/job";

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {
  loading:boolean
  currentUser: number
  posted: boolean
  user: User
  requiredcondition:boolean;
  uploadcondition: boolean;
  dataLoaded: boolean;
  all_jobs: Job[];

  jobForm = this.formBuilder.group({
    job_text: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: JobsService) {
    this.requiredcondition = false;
    this.posted = false;
  }

  async ngOnInit() {
    this.dataLoaded=false;
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);

    await this.service.getAllJobs().toPromise().then(response => this.all_jobs=response);
    console.log(this.all_jobs);
    // await this.webService.getFriends(this.currentUser).toPromise().then(friend => this.friends_=friend);
    // for(let friend of this.friends_){
    //   if(friend.user_one==this.currentUser)
    //     await this.service.getUser(friend.user_two).toPromise().then(user=>this.chat_users.push(user));
    //   else
    //     await this.service.getUser(friend.user_one).toPromise().then(user=>this.chat_users.push(user));
    // }
    // this.user=this.chat_users[0];
    // await this.openChat(this.chat_users[0].id);
    this.dataLoaded=true;
  }

  onSubmit(): void {
    console.log('on submit ' + this.jobForm.value.job_text);
    if(this.jobForm.value.job_text==="" || this.jobForm.value.job_text===null) {
      this.requiredcondition = true;
      this.jobForm.reset();
    }
    else {
      this.service.saveNewJob(this.jobForm.value.job_text,this.user).subscribe(data=>this.uploadcondition=true);
    }
  }

}
