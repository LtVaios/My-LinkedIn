import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Friends} from "../model/friends";
import {User} from "../model/user";
import {Likes} from "../model/likes";
import {Application} from "../model/application";
import {Comment} from "../model/comment";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  private friendsUrl = 'https://localhost:8443/friends';
  private usersUrl = 'https://localhost:8443/users';
  private postLikesUrl = 'https://localhost:8443/likes/touser';
  private jobLikesUrl = 'https://localhost:8443/applications/touser';
  private commentsUrl = 'https://localhost:8443/comments/touser';

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

  deleteFriendship(id: number){
    return this.http.delete(this.friendsUrl+"/"+id);
  }

  getPostLikes(user_id: number):Observable<Likes[]>{
      return this.http.get<Likes[]>(this.postLikesUrl+'?userid='+user_id);
  }

  getJobLikes(user_id: number):Observable<Application[]>{
    return this.http.get<Application[]>(this.jobLikesUrl+'?userid='+user_id);
  }

  getComments(user_id: number):Observable<Comment[]>{
    return this.http.get<Comment[]>(this.commentsUrl+'?userid='+user_id);
  }
}
