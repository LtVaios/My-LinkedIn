import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user";
import {Job} from "../../model/job";
import {SharedService} from "../../shared.service";
import {FormBuilder} from "@angular/forms";
import {JobsService} from "../jobs.service";
import {Application} from "../../model/application";
import {ActivatedRoute} from "@angular/router";
import {colors} from "@angular/cli/utilities/color";

@Component({
  selector: 'app-job-id',
  templateUrl: './job-id.component.html',
  styleUrls: ['./job-id.component.css']
})
export class JobIdComponent implements OnInit {

  currentUser: number;
  user: User;
  dataLoaded: boolean;
  job: Job;
  applied: boolean;
  applicants_count: number;
  applications: Application[];
  current_application: Application;
  tab: number;

  constructor(private sharedService: SharedService,
              private jobs_service: JobsService,
              private route:ActivatedRoute) {
  }

  async ngOnInit() {
    this.dataLoaded=false;
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    var job_id: number;
    job_id =0;
    await this.route.paramMap.subscribe( paramMap => {
      job_id = Number(paramMap.get('id'));
    });
    await this.jobs_service.getJob(job_id).toPromise().then( response => this.job = response);
    await this.jobs_service.getUser(this.currentUser).toPromise().then(response => this.user = response);
    this.applied = false;
    await this.jobs_service.getApplications(job_id).toPromise().then(response => this.applications=response);

    this.applicants_count = this.applications.length;
    for (let application of this.applications) {
      if (application.user.id == this.currentUser) {
          this.applied = true;
        break;
      }
    }

    this.dataLoaded=true;
  }


  likeJob(job: Job): void{
    this.applied=true;
    this.applicants_count += 1;
    var jl = new Application();
    jl.job = job;
    jl.user = this.user;
    jl.createdDate = new Date();
    console.log(jl);
    this.jobs_service.saveLike(jl).subscribe((data=>console.log(data)));
  }

  local(d: Date): string{
    return new Date(d).toLocaleString();
  }

  imagesrc(img: any): string{
    if (img == null) return "";
    return 'data:image/jpeg;base64,'+img.picByte;
  }

  openApplication(appl: Application) {
    this.tab = appl.id;
    this.current_application = appl;
  }
}
