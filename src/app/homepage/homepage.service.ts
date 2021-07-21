import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post";

const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class HomepageService {
  private postsUrl = 'http://localhost:8080/posts';
  newpost:Post
  constructor(private http: HttpClient) { }

  saveNewPost(pb:string,uid:number): Observable<Post> {
    console.log("saving new post");
    this.newpost=new Post();
    this.newpost.post_body=pb;
    this.newpost.user_id=uid;
    return this.http.post<Post>(this.postsUrl,this.newpost);
  }
}
