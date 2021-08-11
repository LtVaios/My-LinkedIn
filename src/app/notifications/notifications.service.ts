import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Friends} from "../model/friends";
import {User} from "../model/user";
import {Likes} from "../model/likes";
import {JobLike} from "../model/joblike";
import {Comment} from "../model/comment";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  private friendsUrl = 'http://localhost:8080/friends';
  private usersUrl = 'http://localhost:8080/users';
  private postLikesUrl = 'http://localhost:8080/likes/touser';
  private jobLikesUrl = 'http://localhost:8080/joblikes/touser';
  private commentsUrl = 'http://localhost:8080/comments/touser';

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

  getPostLikes(user_id: number):Observable<Likes[]>{
      return this.http.get<Likes[]>(this.postLikesUrl+'?userid='+user_id);
  }

  getJobLikes(user_id: number):Observable<JobLike[]>{
    return this.http.get<JobLike[]>(this.jobLikesUrl+'?userid='+user_id);
  }

  getComments(user_id: number):Observable<Comment[]>{
    return this.http.get<Comment[]>(this.commentsUrl+'?userid='+user_id);
  }
}
