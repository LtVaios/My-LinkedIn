import { Injectable } from '@angular/core';
import {Post} from "../model/post";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Job} from "../model/job";
import {User} from "../model/user";
import {Router} from "@angular/router";
import {JobLike} from "../model/joblike";
import {Likes} from "../model/likes";
import {PostView} from "../model/postview";
import {JobView} from "../model/jobview";

@Injectable({
  providedIn: 'root'
})

export class JobsService {
  private jobsUrl = 'https://localhost:8443/jobs';
  private usersUrl = 'https://localhost:8443/users';
  private joblikesUrl = 'https://localhost:8443/joblikes';
  private viewsUrl = 'https://localhost:8443/jobviews/addview';
  private recommendedUrl = 'https://localhost:8443/recommend/jobs';

  constructor(private http: HttpClient) { }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  saveNewJob(jb:string,user:User): Observable<Post> {
    console.log("saving new job");
    var newjob: Job;
    newjob=new Job();
    newjob.body=jb;
    newjob.user=user;
    newjob.createdDate = new Date();
    return this.http.post<Post>(this.jobsUrl, newjob);
  }

  postViews(jobs:Job[], user:User): void {
    for (let job of jobs) {
      let view: JobView = new JobView();
      view.job = job;
      view.user = user;
      view.createdDate = new Date();
      this.http.post<JobView>(this.viewsUrl, view).subscribe();
    }
  }

  getMyJobs(user:User): Observable<Job[]> {
    return this.http.get<Job[]>(this.jobsUrl+"/ofuser/"+user.id);
  }

  getAllJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(this.jobsUrl);
  }

  getJob(job_id: number): Observable<Job> {
    return this.http.get<Job>(this.jobsUrl+"/"+job_id);
  }

  // getFriendsJobs(user_id: number): Observable<Job[]> { {
  //   return this.http.get<Job>(this.jobsUrl+"/"+job_id);
  // }

  getJobsBySkills(skills: string): Observable<Job[]> {
    if (skills == null || skills == '')
      return this.http.get<Job[]>(this.jobsUrl);
    else
      // console.log(this.jobsUrl+'?search='+encodeURIComponent(skills));
      return this.http.get<Job[]>(this.jobsUrl+'?search='+encodeURIComponent(skills));
  }

  saveLike(jl: JobLike): Observable<Post> {
    console.log(jl);
    return this.http.post<Post>(this.joblikesUrl, jl);
  }

  getJobLikes(job_id:number): Observable<JobLike[]>{
    return this.http.get<JobLike[]>(this.joblikesUrl+"/ofpost/"+job_id);
  }

  getRecommendedJobs(user_id: number): Observable<Job[]>{
    return this.http.get<Job[]>(this.recommendedUrl+"/"+user_id);
  }
}
