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
  private usersUrl = 'https://localhost:8443/users';
  private friendsUrl = 'https://localhost:8443/friends';

  constructor(private http: HttpClient,private router: Router) { }

  getUser(user: string): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/"+user);
  }

  getFriendsAndRequests(id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/findall/' + id);
  }

  sendFriendRequest(u1:number,u2:number): Observable<Friends>{
    var friends:Friends;
    friends=new Friends();
    friends.user_one=u1;
    friends.user_two=u2;
    friends.state="pending";
    return this.http.post<Friends>(this.friendsUrl,friends);
  }

}
