import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {Post} from "../model/post";
import {HomepageService} from "./homepage.service";
import {FormBuilder} from "@angular/forms";
import {SharedService} from "../shared.service";
import {Friends} from "../model/friends";
import {empty} from "rxjs/internal/Observer";
import {Likes} from "../model/likes";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {
  requiredcondition:boolean;
  loading:boolean;
  uploaded:boolean;
  posts: Post[];
  html_posts: [Post,boolean][];
  friends: Friends[];
  currentuser:number;

  postForm = this.formBuilder.group({
    post_text: ''
  });

  constructor(private service: HomepageService,
              private sharedService: SharedService,
              private formBuilder: FormBuilder) {
              this.html_posts=[];
              this.requiredcondition = false;
              this.loading=false;
  }

  async ngOnInit(){
    this.sharedService.curr_user.subscribe(user => this.currentuser=user);
    this.loadPosts();
  }

  async loadPosts(){
    this.loading=true;
    let temp: Post[];
    let likes:Likes[];
    let likes_id:number[];
    temp=[];
    likes_id=[];
    likes=[];
    this.html_posts=[];
    this.posts=[];
    await this.service.getPosts(this.currentuser).toPromise().then(post=>this.posts=post);
    await this.service.getFriends(this.currentuser).toPromise().then(response=> this.friends=response);

    //adding our friends' posts into the temporary post list
    for (let f of this.friends) {
      if (f.user_one == this.currentuser)
        await this.service.getPosts(f.user_two).toPromise().then(response => temp = response);
      else
        await this.service.getPosts(f.user_one).toPromise().then(response => temp = response);
    }

    //adding our posts in the temporary post list
    for(let p of this.posts)
      temp.push(p);

    //adding the posts with the boolean indicator that shows if the user has liked the current post
    for (let p of temp) {
      likes_id=[];
      likes=[];
      await this.service.getPostLikes(p.id).toPromise().then(response=>likes=response);
      for(let l of likes)
        likes_id.push(l.user.id)
      if(likes_id.includes(this.currentuser))
        this.html_posts.push([p, true]);
      else
        this.html_posts.push([p, false]);
    }
    this.loading=false;
  }

  async submitPost() {
    this.loading=true;
    this.requiredcondition=false;
    this.uploaded=false;
    if(this.postForm.value.post_text==="" || this.postForm.value.post_text===null) {
      this.requiredcondition = true;
      this.loading=false;
      this.postForm.reset();
    }
    else {
      this.service.saveNewPost(this.postForm.value.post_text, this.currentuser).subscribe(data => console.log(data));
      await new Promise(f => setTimeout(f, 2000));
      this.postForm.reset();
      this.loading=false;
      this.uploaded=true;
    }
    await this.loadPosts();
  }

  async likePost(p:Post){
    this.loading=true;
    let l:Likes;
    l=new Likes();
    await this.service.getUser(this.currentuser).toPromise().then(response=>l.user=response)
    l.post=p;
    await this.service.saveLike(this.currentuser,l).toPromise().then(response=>console.log(response))
    this.loading=false;
    await this.loadPosts();
  }

}
