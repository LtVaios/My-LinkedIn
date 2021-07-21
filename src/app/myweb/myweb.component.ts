import { Component, OnInit } from '@angular/core';
import {Friends} from '../model/friends';
import {MywebService} from './myweb.service';
import {User} from '../model/user';
import {FormBuilder} from "@angular/forms";
import {SharedService} from "../shared.service";


@Component({
  selector: 'app-myweb',
  templateUrl: './myweb.component.html',
  styleUrls: ['./myweb.component.css']
})
export class MywebComponent implements OnInit {
  private friendsUrl = 'http://localhost:8080/friends';
  private usersUrl = 'http://localhost:8080/users';
  friends_: Friends[];
  users: User[];
  currentUser:number;
  dataLoaded: Promise<boolean>;
  searchForm = this.formBuilder.group({
    searchbar: ''
  });
  constructor(private formBuilder: FormBuilder,private service: MywebService,private sharedService: SharedService) { this.friends_=[]; this.users=[]}

  async ngOnInit() {
    this.dataLoaded= Promise.resolve(false);
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    await this.service.getFriends(this.currentUser).toPromise().then(friend => this.friends_=friend);
    for(let friend of this.friends_){
      if(friend.user_one==this.currentUser)
        await this.service.getUser(friend.user_two).toPromise().then(user=>this.users.push(user));
      else
        await this.service.getUser(friend.user_one).toPromise().then(user=>this.users.push(user));
    }
    console.log("got users: " +this.users[0].firstName);
    this.dataLoaded= Promise.resolve(true);
  }

  async onSearch() {
    ;
  }

}
