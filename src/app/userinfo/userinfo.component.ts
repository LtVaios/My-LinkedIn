import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {ActivatedRoute} from "@angular/router";
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
  sent:boolean
  path_id: string | null
  dataLoaded: Promise<boolean>
  friends_: Friends[]
  is_friends: boolean
  other_userimage: any;

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
      this.is_friends = false;
      await this.service.getUser(this.path_id).toPromise().then((response) => this.other_user = response);
      this.current_user_id=parseInt(<string>localStorage.getItem('currentuser'))
      await this.service.getFriendsAndRequests(this.current_user_id).toPromise().then(friend => this.friends_=friend);
      for(let friend of this.friends_){
        console.log(friend);
        if((friend.user_one==this.other_user.id || friend.user_two==this.other_user.id) && friend.state === "completed")
          this.is_friends = true;
        if((friend.user_one==this.other_user.id || friend.user_two==this.other_user.id) && friend.state === "pending")
          this.sent = true;
      }
    }
    if (this.other_user.img!==null)
      this.other_userimage = 'data:image/jpeg;base64,' + this.other_user.img.picByte;
    this.dataLoaded= Promise.resolve(true);
  }

  async sendRequest(){
    await this.service.sendFriendRequest(this.current_user_id,this.other_user.id).toPromise().then(response => console.log(response))
    this.sent=true;
  }

}
