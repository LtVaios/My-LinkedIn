import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Comment} from "../model/comment";
import {HttpClient} from "@angular/common/http";
import {Post} from "../model/post";
import {Likes} from "../model/likes";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  private postsUrl = 'http://localhost:8080/posts';
  private likesUrl = 'http://localhost:8080/likes';
  private friendsUrl = 'http://localhost:8080/friends';
  private usersUrl = 'http://localhost:8080/users';
  private commentsUrl = 'http://localhost:8080/comments';

  constructor(private http: HttpClient) { }

  getPostComments(post_id:number): Observable<Comment[]>{
    return this.http.get<Comment[]>(this.commentsUrl+"/ofpost/"+post_id);
  }

  getPost(id:number): Observable<Post> {
    return this.http.get<Post>(this.postsUrl+"/"+id);
  }

  getPostLikes(post_id:number): Observable<Likes[]>{
    return this.http.get<Likes[]>(this.likesUrl+"/ofpost/"+post_id);
  }

  saveLike(user_id:number,l:Likes): Observable<Post>{
    return this.http.post<Post>(this.likesUrl+"/addlike",l);
  }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  saveNewComment(text:string,p:Post,u:User): Observable<Comment>{
    let comm: Comment=new Comment();
    comm.comment_text=text
    comm.post=p
    comm.user=u
    console.log(comm)
    return this.http.post<Comment>(this.commentsUrl,comm);
  }
}
