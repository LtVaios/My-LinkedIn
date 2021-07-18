import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../model/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient,private router: Router) {
  }

  changePass(user: string, new_pass: string): Observable<User>{
    return this.http.put<User>(this.usersUrl+"/"+user, "pass,"+new_pass);
  }

  changeEmail(user: string, new_email: string): Observable<User>{
    console.log("email,"+new_email+"URL:"+this.usersUrl+"/"+user);
    return this.http.put<User>(this.usersUrl+"/"+user, "email,"+new_email);
  }
}
