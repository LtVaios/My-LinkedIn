import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post";
import {Friends} from "../model/friends";

const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class HomepageService {
  private postsUrl = 'http://localhost:8080/posts';
  private friendsUrl = 'http://localhost:8080/friends';
  newpost:Post
  constructor(private http: HttpClient) { }

  saveNewPost(pb:string,uid:number): Observable<Post> {
    console.log("saving new post");
    this.newpost=new Post();
    this.newpost.post_body=pb;
    let date_time =new Date();
    this.newpost.date_time=date_time.toLocaleString();
    console.log(date_time)
    return this.http.post<Post>(this.postsUrl+"/"+uid,this.newpost);
  }

  getPosts(uid:number): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl+"/ofuser/"+uid);
  }

  getFriends(id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/' + id);
  }

}
