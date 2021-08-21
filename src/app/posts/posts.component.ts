import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserinfoService} from "../userinfo/userinfo.service";
import {PostsService} from "./posts.service";
import {Comment} from "../model/comment";
import {Post} from "../model/post";
import {Likes} from "../model/likes";
import {SharedService} from "../shared.service";
import {FormBuilder} from "@angular/forms";
import {User} from "../model/user";
import {Image} from "../model/image";

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  post_id: string | null;
  loading:boolean;
  requiredcondition:boolean
  currentuser: number;
  comms: Comment[];
  likes: Likes[];
  post: Post;
  isLiked: boolean;
  likes_count: number;
  commentForm = this.formBuilder.group({
    comm_text: ''
  });

  constructor(private route:ActivatedRoute,
              private service: PostsService,
              private sharedService: SharedService,
              private formBuilder: FormBuilder) {this.post=new Post(); this.isLiked=false; }

  async ngOnInit() {
    this.currentuser=parseInt(<string>localStorage.getItem('currentuser'))
    this.route.paramMap.subscribe( paramMap => {
      this.post_id = paramMap.get('id');
    });
    this.load_post();
  }

  async load_post(){
    this.loading=true;
    let flag:boolean=false;
    this.comms=[];
    this.likes=[];

    await this.service.getPost(Number(this.post_id)).toPromise().then(response => this.post=response)
    await this.service.getPostComments(Number(this.post_id)).toPromise().then(response=>this.comms=response)
    await this.service.getPostLikes(Number(this.post_id)).toPromise().then(response=>this.likes=response);
    this.likes_count = this.likes.length;
    this.isLiked = false;
    for(let l of this.likes)
      if(l.user.id==this.currentuser){
        this.isLiked = true;
        flag=true;
        break;
      }

    this.loading=false;
  }

  async likePost(p:Post){
    this.isLiked = true;
    this.likes_count += 1;
    let l:Likes;
    l=new Likes();
    await this.service.getUser(this.currentuser).toPromise().then(response=>l.user=response)
    l.post=p;
    l.createdDate = new Date();
    await this.service.saveLike(this.currentuser,l).toPromise().then(response=>console.log(response))

  }

  async submitComment() {
    this.loading=true
    this.requiredcondition = false;
    if (this.commentForm.value.comm_text === "" || this.commentForm.value.comm_text === null) {
      this.requiredcondition = true;
      this.commentForm.reset();
    }
    else {
      let u:User;
      u = new User();
      await this.service.getUser(this.currentuser).toPromise().then(result => u=result)
      await this.service.saveNewComment(this.commentForm.value.comm_text,this.post,u).subscribe()
      await new Promise(f => setTimeout(f, 2000));
      this.commentForm.reset();
      this.load_post()
      this.loading=false;
    }
  }

  imagesrc(img: Image): string{
    if (img == null) return "";
    return 'data:image/jpeg;base64,'+img.picByte;
  }
}
