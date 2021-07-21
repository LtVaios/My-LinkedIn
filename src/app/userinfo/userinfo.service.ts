import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Friends} from "../model/friends";

@Injectable({
  providedIn: 'root'
})
export class UserinfoService {
  private usersUrl = 'http://localhost:8080/users';
  private friendsUrl = 'http://localhost:8080/friends';

  constructor(private http: HttpClient,private router: Router) { }

  getUser(user: string): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/"+user);
  }

  getFriends(id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/' + id);
  }
}
