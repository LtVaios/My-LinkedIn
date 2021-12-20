import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post";
import {User} from "../model/user";
import {Friends} from "../model/friends";
import {Likes} from "../model/likes";
import {Comment} from "../model/comment";
import {PostView} from "../model/postview";

const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class HomepageService {
  private postsUrl = 'https://localhost:8443/posts';
  private likesUrl = 'https://localhost:8443/likes';
  private friendsUrl = 'https://localhost:8443/friends';
  private usersUrl = 'https://localhost:8443/users';
  private commentsUrl = 'https://localhost:8443/comments';
  private viewsUrl = 'https://localhost:8443/postviews/addview';
  private recommendedUrl = 'https://localhost:8443/recommend/posts';
  constructor(private http: HttpClient) { }

  saveNewPost(pb:string,uid:number): Observable<Post> {
    console.log("saving new post");
    let new_post:Post = new Post();
    new_post.post_body=pb;
    let date_time =new Date();
    new_post.date_time=date_time.toLocaleString();
    //console.log(date_time)
    return this.http.post<Post>(this.postsUrl+"/"+uid,new_post);
  }

  getPosts(uid:number): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl+"/ofuser/"+uid);
  }

  postViews(posts:Post[], user:User): void {
    for (let post of posts) {
      let view: PostView = new PostView();
      view.post = post;
      view.user = user;
      view.createdDate = new Date();
      this.http.post<PostView>(this.viewsUrl, view).subscribe();
    }
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

  getRecommendedPosts(user_id: number): Observable<Post[]>{
    return this.http.get<Post[]>(this.recommendedUrl+"/"+user_id);
  }

}
