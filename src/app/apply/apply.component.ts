import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {User} from "../model/user";
import {ApplyService} from "./apply.service";
import {Job} from "../model/job";
import {Application} from "../model/application";
import {FormBuilder} from "@angular/forms";

enum application_progress{
  init,
  loading,
  error,
  completed
}

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit {

  ref_application_progress = application_progress;
  job_id: number;
  text: string;
  current_user_id: number;
  current_user: User;
  job: Job;
  userLoaded: boolean;
  jobLoaded: boolean;

  prev_appl: Application;

  applied: application_progress;

  applicants_count: number;
  user_already_applied: boolean;
  textForm = this.formBuilder.group({
    text: ''
  });

  private applications: Application[];

  constructor(private route: ActivatedRoute,
              private service: ApplyService,
              private formBuilder: FormBuilder) {
    this.applied = application_progress.init;
    this.userLoaded = false;
    this.user_already_applied = false;
    this.jobLoaded = false;
    this.applicants_count = 0;
  }

  ngOnInit(): void {
    this.current_user_id=parseInt(<string>localStorage.getItem('currentuser'));

    this.load_job().then(r => console.log("after job"));
    this.service.getUser(this.current_user_id).subscribe(data => {
      this.current_user = data;
      this.userLoaded = true;
    });
  }

  async load_job(){
    console.log("in load job");
    var job_id:number = 0;
    this.route.params.subscribe(
      params => {
        job_id = parseInt(params['job_id']);
        console.log("inside")
        this.job_id = job_id;
        console.log("in load job");
        console.log(this.job_id);
        this.service.getJob(this.job_id).subscribe(
          data => {
            this.job = data;
            this.jobLoaded = true;
          }
        );

        this.service.getApplications(this.job_id).subscribe(data => {
          this.applications = data;
          for (var appl of this.applications) {
            if (appl.user.id == this.current_user_id && appl.job.id == this.job_id) {
              this.prev_appl = appl;
              this.user_already_applied = true;
              break;
            }
          }
          this.applicants_count = this.applications.length;
          if (this.user_already_applied)
            this.applicants_count-=1;
        });

      },
      err => console.log(err)
    );

  }

  onSubmit(): void {
    var appl: Application = new Application();
    if (this.user_already_applied){
      appl = this.prev_appl;
    }
    appl.text = this.textForm.value.text;
    console.log(this.text);
    appl.job = this.job;
    appl.createdDate = new Date();
    appl.user = this.current_user;
    this.service.saveApplication(appl).subscribe(
      next => this.applied = application_progress.loading,
      err => this.applied = application_progress.error,
      ()  => this.applied = application_progress.completed
    );
  }

  local(d: Date): string{
    return new Date(d).toLocaleString();
  }

  imagesrc(img: any): string{
    if (img == null) return "";
    return 'data:image/jpeg;base64,'+img.picByte;
  }
}
