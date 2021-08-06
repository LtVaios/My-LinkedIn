import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post";
import {User} from "../model/user";
import {Friends} from "../model/friends";
import {Likes} from "../model/likes";
import {Comment} from "../model/comment";

const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class HomepageService {
  private postsUrl = 'http://localhost:8080/posts';
  private likesUrl = 'http://localhost:8080/likes';
  private friendsUrl = 'http://localhost:8080/friends';
  private usersUrl = 'http://localhost:8080/users';
  private commentsUrl = 'http://localhost:8080/comments';
  newpost:Post
  constructor(private http: HttpClient) { }

  saveNewPost(pb:string,uid:number): Observable<Post> {
    console.log("saving new post");
    this.newpost=new Post();
    this.newpost.post_body=pb;
    let date_time =new Date();
    this.newpost.date_time=date_time.toLocaleString();
    //console.log(date_time)
    return this.http.post<Post>(this.postsUrl+"/"+uid,this.newpost);
  }

  getPosts(uid:number): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl+"/ofuser/"+uid);
  }

  getFriends(id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/' + id);
  }

  saveLike(user_id:number,l:Likes): Observable<Likes>{
    return this.http.post<Likes>(this.likesUrl+"/addlike",l);
  }

  getPostLikes(post_id:number): Observable<Likes[]>{
    return this.http.get<Likes[]>(this.likesUrl+"/ofpost/"+post_id);
  }

  getUserLikes(user_id:number): Observable<Likes[]>{
    return this.http.get<Likes[]>(this.likesUrl+"/ofuser/"+user_id);
  }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

}
