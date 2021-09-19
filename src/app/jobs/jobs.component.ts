import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../model/user";
import {JobsService} from "./jobs.service";
import {Job} from "../model/job";
import {Application} from "../model/application";

enum progress{
  init,
  loading,
  error,
  completed
}

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {
  ref_progress = progress; /* reference to enum type*/
  post_progress: progress; /* shows the state of a job post */

  currentUser: number; /* id of current user*/
  user: User; /* current user */

  requiredcondition: boolean; /* condition that shows if a job post is empty */
  dataLoaded: boolean;
  all_jobs: Job[]; /* every job that is going to show up on html */
  temp_jobs: Job[]; /* just a temp to do sorting of jobs and then assign it to all_jobs */
  recommended_jobs: Job[]; /* recommended jobs by matrix factorization */
  appliedJobs: Map<Job, boolean>; /* all_jobs' jobs mapped with true or false depending on if the current user haw applied for it */
  applicants_count: Map<Job, number>; /* all_jobs' jobs mapped with their applicants count */

  tab: number; /* for the three tabs: default, most recent and my jobs
                  basically changes the all_jobs list depending on the value of tab */

  jobForm = this.formBuilder.group({
    job_text: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: JobsService) {
    this.requiredcondition = false;
    this.appliedJobs = new Map();
    this.applicants_count = new Map<Job, number>();
    this.post_progress = progress.init;
    this.tab = 0;
    this.dataLoaded=false;
  }


  async ngOnInit() {
    this.dataLoaded=false; /* TODO check dataLoaded*/
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'));
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    this.default();
    this.dataLoaded=true;
  }

  async onSubmit() {
    if(this.jobForm.value.job_text==="" || this.jobForm.value.job_text===null) {
      this.requiredcondition = true;
    }
    else {
      this.service.saveNewJob(this.jobForm.value.job_text,this.user).subscribe(
        data => this.post_progress = progress.loading,
        error => {
          this.post_progress = progress.error;
          },
        async () => {
          await new Promise(f => setTimeout(f, 2000));
          this.post_progress = progress.completed;
          this.jobForm.reset();
          if (this.tab == 0){
            this.default();
          }
          else if (this.tab == 1){
            this.most_recent();
          }
          else if (this.tab == 2){
            this.my_jobs();
          }
        }
      );
    }
  }

  local(d: Date): string{
    return new Date(d).toLocaleString();
  }

  imagesrc(img: any): string{
    if (img == null) return "";
    return 'data:image/jpeg;base64,'+img.picByte;
  }

  async default() {
    this.tab = 0;
    localStorage.setItem('tab', JSON.stringify(this.tab));
    await this.service.getJobsBySkills(this.user.skills).toPromise().then(response => this.all_jobs=response);
    var flag: boolean;
    for (let job of this.all_jobs){
      flag = false;
      var likes: Application[] = [];
      await this.service.getApplications(job.id).toPromise().then(response => likes=response);
      this.applicants_count.set(job, likes.length);
      for (let like of likes) {
        if (like.user.id == this.currentUser) {
          this.appliedJobs.set(job, true);
          flag=true;
          break;
        }
      }
      if (flag==false){
        this.appliedJobs.set(job, false);
      }

      await this.service.getRecommendedJobs(this.user.id).toPromise().then(response => this.recommended_jobs=response);
      //console.log(this.recommended_jobs);
      var flag: boolean;
      for (let job of this.recommended_jobs) {
        flag = false;
        var likes: Application[] = [];
        await this.service.getApplications(job.id).toPromise().then(response => likes = response);
        this.applicants_count.set(job, likes.length);
        for (let like of likes) {
          if (like.user.id == this.currentUser) {
            this.appliedJobs.set(job, true);
            flag = true;
            break;
          }
        }
        if (flag == false) {
          this.appliedJobs.set(job, false);
        }
      }

    }
  }

  async most_recent() {
    this.tab = 1;
    localStorage.setItem('tab', JSON.stringify(this.tab));
    var flag: boolean;

    await this.service.getAllJobs().toPromise().then(response => this.temp_jobs = response);

    for (let job of this.temp_jobs) {
      flag = false;
      var likes: Application[] = [];
      await this.service.getApplications(job.id).toPromise().then(response => likes = response);
      this.applicants_count.set(job, likes.length);
      for (let like of likes) {
        if (like.user.id == this.currentUser) {
          this.appliedJobs.set(job, true);
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.appliedJobs.set(job, false);
      }
    }
    this.temp_jobs.sort((a, b) =>
      (new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime())
    );
    this.all_jobs = this.temp_jobs;
  }

  async my_jobs() {
    this.tab = 2;
    localStorage.setItem('tab', JSON.stringify(this.tab));

    await this.service.getMyJobs(this.user).toPromise().then(response => this.temp_jobs = response);
    var flag: boolean;
    for (let job of this.temp_jobs) {
      flag = false;
      var likes: Application[] = [];
      await this.service.getApplications(job.id).toPromise().then(response => likes = response);
      this.applicants_count.set(job, likes.length);
      for (let like of likes) {
        if (like.user.id == this.currentUser) {
          this.appliedJobs.set(job, true);
          flag = true;
          break;
        }
      }
      if (!flag) {
        this.appliedJobs.set(job, false);
      }
    }
    this.temp_jobs.sort((a, b) =>
      (new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime())
    );
    this.all_jobs = this.temp_jobs;
  }
}
