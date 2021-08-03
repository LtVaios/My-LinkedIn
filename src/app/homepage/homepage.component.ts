import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {Post} from "../model/post";
import {HomepageService} from "./homepage.service";
import {FormBuilder} from "@angular/forms";
import {SharedService} from "../shared.service";
import {Friends} from "../model/friends";

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
  friends: Friends[];
  currentuser:number;

  postForm = this.formBuilder.group({
    post_text: ''
  });

  constructor(private service: HomepageService,
              private sharedService: SharedService,
              private formBuilder: FormBuilder) { this.requiredcondition = false; this.loading=false;}

  async ngOnInit(){
    this.sharedService.curr_user.subscribe(user => this.currentuser=user);
    this.loadPosts();
  }

  async loadPosts(){
    this.loading=true;
    let temp: Post[];
    temp=[];

    await this.service.getPosts(this.currentuser).toPromise().then(post=>this.posts=post);
    await this.service.getFriends(this.currentuser).toPromise().then(response=> this.friends=response)
    for (let f of this.friends){
      if(f.user_one==this.currentuser) {
        await this.service.getPosts(f.user_two).toPromise().then(response => temp = response)
        for(let p of temp)
          this.posts.push(p);
      }
      else{
        await this.service.getPosts(f.user_one).toPromise().then(response => temp = response)
        for(let p of temp)
          this.posts.push(p);
      }
    }
    this.loading=false;
  }

  async submitPost() {
    this.loading=true;
    if(this.postForm.value.post_text==="" || this.postForm.value.post_text===null) {
      this.requiredcondition = true;
      this.postForm.reset();
    }
    else {
      this.service.saveNewPost(this.postForm.value.post_text, this.currentuser).subscribe(data => console.log(data));
      await new Promise(f => setTimeout(f, 2000));
      this.postForm.reset();
      this.loading=false;
      this.uploaded=true;
    }
    this.loadPosts();
  }
}
