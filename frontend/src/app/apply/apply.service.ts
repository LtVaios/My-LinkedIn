import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";
import {Observable} from "rxjs";
import {Application} from "../model/application";
import {Job} from "../model/job";

@Injectable({
  providedIn: 'root'
})
export class ApplyService {

  private applyUrl: string = 'https://localhost:8443/applications';
  private usersUrl = 'https://localhost:8443/users';
  private jobsUrl = 'https://localhost:8443/jobs';

  constructor(private http: HttpClient) { }

  saveApplication(appl: Application): Observable<Application> {
    return this.http.post<Application>(this.applyUrl, appl);
  }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  getJob(job_id: number): Observable<Job> {
    return this.http.get<Job>(this.jobsUrl+"/"+job_id);
  }

  getApplications(job_id:number): Observable<Application[]>{
    return this.http.get<Application[]>(this.applyUrl+"/ofpost/"+job_id);
  }
}
