import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../model/user";
import {JobsService} from "./jobs.service";
import {Job} from "../model/job";
import {Application} from "../model/application";

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {
  loading:boolean;
  currentUser: number;
  posted: boolean;
  user: User;
  requiredcondition:boolean;
  uploadcondition: boolean;
  dataLoaded: boolean;
  all_jobs: Job[];
  recommended_jobs: Job[];
  temp_jobs: Job[];
  appliedJobs: Map<Job, boolean>;
  applicants_count: Map<Job, number>;
  tab: number;
  inapply: boolean;
  bodyText:string;

  jobForm = this.formBuilder.group({
    job_text: ''
  });

  constructor(private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private service: JobsService) {
    this.requiredcondition = false;
    this.posted = false;
    this.appliedJobs = new Map();
    this.applicants_count = new Map<Job, number>();
    this.inapply = false;
  }


  async ngOnInit() {
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    await this.loadJobs()
  }

  async loadJobs(){
    this.posted=false;
    this.dataLoaded=false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);

    this.default();

    this.dataLoaded=true;
  }

  async onSubmit() {
    this.loading=true
    if(this.jobForm.value.job_text==="" || this.jobForm.value.job_text===null) {
      this.requiredcondition = true;
    }
    else {
      this.service.saveNewJob(this.jobForm.value.job_text,this.user).subscribe(data=>this.uploadcondition=true);
      await new Promise(f => setTimeout(f, 2000));
      this.jobForm.reset();
      this.posted=true;
      this.loading=false
      await this.loadJobs()
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
      console.log(this.recommended_jobs);
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

  // async connected_jobs() {
  //   this.tab = 3;
  //   localStorage.setItem('tab', JSON.stringify(this.tab));
  //
  //   await this.service.getFriendsJobs(this.user).toPromise().then(response => this.temp_jobs = response);
  //   var flag: boolean;
  //   for (let job of this.temp_jobs) {
  //     flag = false;
  //     var likes: Application[] = [];
  //     await this.service.getJobLikes(job.id).toPromise().then(response => likes = response);
  //     for (let like of likes) {
  //       if (like.user.id == this.currentUser) {
  //         this.likedJobs.set(job, true);
  //         flag = true;
  //         break;
  //       }
  //     }
  //     if (flag == false) {
  //       this.likedJobs.set(job, false);
  //     }
  //   }
  //   this.temp_jobs.sort((a, b) =>
  //     (new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime())
  //   );
  //   this.all_jobs = this.temp_jobs;
  // }
}
