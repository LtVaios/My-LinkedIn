import { Component, OnInit } from '@angular/core';
import {DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import {User} from "../model/user";
import {AdminHomepageService} from "./admin-homepage.service";
import {Likes} from "../model/likes";
import {Friends} from "../model/friends";
import {Post} from "../model/post";
import {Job} from "../model/job";
import * as JsonToXML from "js2xmlparser";

@Component({
  selector: 'app-admin-homepage',
  templateUrl: './admin-homepage.component.html',
  styleUrls: ['./admin-homepage.component.css']
})
export class AdminHomepageComponent implements OnInit {
  dataLoaded:boolean
  downloadJsonHref:SafeUrl
  user: User
  likes: Likes[]
  friends: Friends[]
  comments: Comment[]
  posts: Post[]
  jobs: Job[]
  users: User[]
  jsons: Map<number,SafeUrl>
  xmls: Map<number,any>

  constructor(private service: AdminHomepageService,private sanitizer: DomSanitizer) { }

  async ngOnInit() {
    this.jsons=new Map<number,any>()
    this.xmls=new Map<number,any>()
    await this.loadUsers()
    await this.load_JSONSand_XMLS()
  }

  async loadUsers(){
    this.dataLoaded=false
    await this.service.getUsers().toPromise().then(response=>this.users=response)
  }


  async load_JSONSand_XMLS() {
    this.dataLoaded = false
    var id:number;
    for (let u of this.users){
      id=u.id;
      await this.service.getUser(id).toPromise().then(response => this.user = response)
      await this.service.getUserComments(id).toPromise().then(response => this.comments = response)
      await this.service.getPosts(id).toPromise().then(response => this.posts = response)
      await this.service.getFriends(id).toPromise().then(response => this.friends = response)
      await this.service.getJobs(id).toPromise().then(response => this.jobs = response)
      await this.service.getUserLikes(id).toPromise().then(response => this.likes = response)
      var combined=new Array()
      combined.push(this.user)
      combined.push(this.friends)
      combined.push(this.posts)
      combined.push(this.jobs)
      combined.push(this.comments)
      combined.push(this.likes)
      var theJSON = JSON.stringify(combined);
      var uri = this.sanitizer.bypassSecurityTrustUrl("data:text/json;charset=UTF-8," + encodeURIComponent(theJSON));
      this.jsons.set(id, uri)

      var obj=JsonToXML.parse("user", this.user)
      var url = this.sanitizer.bypassSecurityTrustUrl("data:text/xml;charset=UTF-8," + encodeURIComponent(obj));
      this.xmls.set(id,url)
    }
    this.dataLoaded=true
  }

}
