import { Component, OnInit } from '@angular/core';
import {Friends} from '../model/friends';
import {MywebService} from './myweb.service';
import {User} from '../model/user';
import {FormBuilder} from "@angular/forms";
import {SharedService} from "../shared.service";
import {Router} from "@angular/router";
import {ChatService} from "../chat/chat.service";


@Component({
  selector: 'app-myweb',
  templateUrl: './myweb.component.html',
  styleUrls: ['./myweb.component.css']
})
export class MywebComponent implements OnInit {
  private friendsUrl = 'https://localhost:8443/friends';
  private usersUrl = 'https://localhost:8443/users';
  friends_: Friends[];
  users: User[];
  currentUser:number;
  dataLoaded: boolean;
  filledCondition:boolean;
  users_images: Map<User,string>;

  searchForm = this.formBuilder.group({
    searchbar: ''
  });
  constructor(private formBuilder: FormBuilder,
              private service: MywebService,
              private sharedService: SharedService,
              private ChatService: ChatService,
              private router: Router) { this.friends_=[]; this.users=[]; this.users_images=new Map<User, string>();}

  async ngOnInit() {
    this.dataLoaded= false;
    //await new Promise(f => setTimeout(f, 5000));
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    await this.service.getFriends(this.currentUser).toPromise().then(friend => this.friends_=friend);
    for(let friend of this.friends_){
      if(friend.user_one==this.currentUser)
        await this.service.getUser(friend.user_two).toPromise().then(user=> {
          this.users.push(user);
          if (user.img!==null) {
            this.users_images.set(user,'data:image/jpeg;base64,' + user.img.picByte);
          }
        });
      else
        await this.service.getUser(friend.user_one).toPromise().then(user=> {
          this.users.push(user);
          if (user.img!==null) {
            this.users_images.set(user,'data:image/jpeg;base64,' + user.img.picByte);
          }
        });
    }
    this.dataLoaded= true;
  }

  async onSearch() {
    this.filledCondition=false;
    if(this.searchForm.value.searchbar==="" || this.searchForm.value.searchbar===null) {
      this.filledCondition = true;
      this.searchForm.reset();
    }
    else {
      this.sharedService.editSearch(this.searchForm.value.searchbar);
      this.router.navigate(['./myweb/search']);
    }
  }

}
