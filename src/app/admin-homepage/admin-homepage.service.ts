import { Injectable } from '@angular/core';
import {User} from '../model/user';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Friends} from "../model/friends";
import {Post} from "../model/post";
import {Likes} from "../model/likes";
import {Job} from "../model/job";

@Injectable({
  providedIn: 'root'
})
export class AdminHomepageService {
  private postsUrl = 'http://localhost:8080/posts';
  private likesUrl = 'http://localhost:8080/likes';
  private usersUrl = 'http://localhost:8080/users';
  private friendsUrl = 'http://localhost:8080/friends';
  private commentsUrl = 'http://localhost:8080/comments';
  private jobsUrl = 'http://localhost:8080/jobs';
  constructor( private http: HttpClient ) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  getUser(user_id: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user_id);
  }

  getFriends(user_id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/' + user_id);
  }

  getPosts(user_id:number): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl+"/ofuser/"+user_id);
  }

  getUserLikes(user_id:number): Observable<Likes[]>{
    return this.http.get<Likes[]>(this.likesUrl+"/ofuser/"+user_id);
  }

  getUserComments(user_id:number): Observable<Comment[]>{
    return this.http.get<Comment[]>(this.commentsUrl+"/ofuser/"+user_id);
  }

  getJobs(user_id:number): Observable<Job[]> {
    return this.http.get<Job[]>(this.jobsUrl+"/"+user_id);
  }

}
