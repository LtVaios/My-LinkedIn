import { Injectable } from '@angular/core';
import {Post} from "../model/post";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Job} from "../model/job";
import {User} from "../model/user";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class JobsService {
  private jobsUrl = 'http://localhost:8080/jobs';
  private usersUrl = 'http://localhost:8080/users';
  newjob: Job;

  constructor(private http: HttpClient) { }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  saveNewJob(jb:string,user:User): Observable<Post> {
    console.log("saving new post");
    this.newjob=new Job();
    this.newjob.body=jb;
    this.newjob.user=user;
    this.newjob.createdDate = new Date();
    return this.http.post<Post>(this.jobsUrl,this.newjob);
  }

  getMyJobs(user:User): Observable<Job[]> {
    return this.http.get<Job[]>(this.jobsUrl+"/"+user.id);
  }

  getAllJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(this.jobsUrl);
  }
}
