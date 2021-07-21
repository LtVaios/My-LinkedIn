import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {ActivatedRoute} from "@angular/router";
import {InfoService} from "../info/info.service";
import {UserinfoService} from "./userinfo.service";
import {SharedService} from "../shared.service";
import {Friends} from "../model/friends";

@Component({
  selector: 'app-userinfo',
  templateUrl: './userinfo.component.html',
  styleUrls: ['./userinfo.component.css']
})
export class UserinfoComponent implements OnInit {
  other_user: User
  current_user_id: number
  current_user: User
  path_id: string | null
  dataLoaded: Promise<boolean>;
  friends_: Friends[];
  is_friends: boolean;

  constructor(private sharedService: SharedService,
              private service: UserinfoService,
              private route:ActivatedRoute) { }

  async ngOnInit() {
    this.dataLoaded= Promise.resolve(false);
    this.route.paramMap.subscribe( paramMap => {
      this.path_id = paramMap.get('id');
    });
    console.log("starting userinfo");
    if (this.path_id != null ){
      console.log("path is not null");
      this.is_friends = false;
      await this.service.getUser(this.path_id).toPromise().then((response) => this.other_user = response);
      console.log("path is not null1");
      this.sharedService.curr_user.subscribe(user => this.current_user_id = user);
      console.log("path is not null2");
      await this.service.getFriends(this.current_user_id).toPromise().then(friend => this.friends_=friend);
      console.log("path is not null3");
      console.log(this.other_user);
      console.log(this.current_user_id);
      console.log(this.friends_);
      for(let friend of this.friends_){
        console.log(friend);
        if((friend.user_one==this.other_user.id || friend.user_two==this.other_user.id) && friend.state == "completed")
          this.is_friends = true;
          console.log("friends");
      }
    }
    this.dataLoaded= Promise.resolve(true);
  }

}
