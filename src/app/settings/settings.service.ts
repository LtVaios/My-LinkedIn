import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../model/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private usersUrl = 'https://localhost:8443/users';

  constructor(private http: HttpClient,private router: Router) {
  }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  updateUser(id: number, user: User): Observable<User>{
    return this.http.post<User>(this.usersUrl, user);
  }
}
