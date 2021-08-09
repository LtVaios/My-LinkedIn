import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {NotificationsService} from "../notifications/notifications.service";
import {Friends} from "../model/friends"
import {User} from "../model/user";
import loader from "@angular-devkit/build-angular/src/webpack/plugins/single-test-transform";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  dataLoaded: boolean;
  friend_requests: Friends[];
  friend_requests_users: User[];
  notifications: any[];
  currentUser: number;

  constructor(private sharedService: SharedService,
              private service: NotificationsService) {
    this.dataLoaded=false;
    this.friend_requests=[];
    this.friend_requests_users=[]
  }

  async ngOnInit() {
    this.dataLoaded=false;
    this.sharedService.curr_user.subscribe(user => this.currentUser=user);
    /* get friend requests */
    await this.service.getFriendRequests(this.currentUser).toPromise().then(response => this.friend_requests = response);
    /* get users */
    for(let friend of this.friend_requests){
        await this.service.getUser(friend.user_one).toPromise().then(user=>this.friend_requests_users.push(user));
    }
    /* get notifications (likes, comments to user's posts) */
    this.dataLoaded=true;
  }

  onAccept(fr: Friends){
    fr.state = 'completed';
    this.service.saveFriendship(fr).subscribe(data=>console.log(data));
  }

}
