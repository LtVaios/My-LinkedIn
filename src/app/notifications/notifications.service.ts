import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Friends} from "../model/friends";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  private friendsUrl = 'http://localhost:8080/friends';
  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  getFriendRequests(userid: number): Observable<Friends[]>{
    return this.http.get<Friends[]>(this.friendsUrl+"/requests?userid="+userid);
  }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  saveFriendship(fr: Friends): Observable<Friends>{
    return this.http.post<Friends>(this.friendsUrl, fr);
  }
}
