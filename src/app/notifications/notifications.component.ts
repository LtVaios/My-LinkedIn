import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {NotificationsService} from "../notifications/notifications.service";
import {Friends} from "../model/friends"
import {User} from "../model/user";
import loader from "@angular-devkit/build-angular/src/webpack/plugins/single-test-transform";
import {Likes} from "../model/likes";
import {JobLike, notification} from "../model/joblike";
import {Comment} from "../model/comment";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  dataLoaded: boolean;
  friend_requests: Friends[];
  friend_requests_users: User[];
  notifications: Array<notification> = [];
  currentUser: number;
  post_likes: Likes[];
  job_likes: JobLike[];
  comments: Comment[];

  constructor(private sharedService: SharedService,
              private service: NotificationsService) {
    this.dataLoaded = false;
    this.friend_requests = [];
    this.friend_requests_users = [];
    this.notifications = [];
  }

  async ngOnInit() {
    this.dataLoaded = false;
    this.sharedService.curr_user.subscribe(user => this.currentUser = user);
    /* get friend requests */
    await this.service.getFriendRequests(this.currentUser).toPromise().then(response => this.friend_requests = response);
    /* get users */
    for (let friend of this.friend_requests) {
      await this.service.getUser(friend.user_one).toPromise().then(user => this.friend_requests_users.push(user));
    }
    /* get notifications (likes, comments to user's posts) */
    await this.service.getPostLikes(this.currentUser).toPromise().then(response => this.post_likes = response);
    await this.service.getJobLikes(this.currentUser).toPromise().then(response => this.job_likes = response);
    await this.service.getComments(this.currentUser).toPromise().then(response => this.comments = response);

    this.notifications.push(...this.post_likes);
    this.notifications.push(...this.job_likes);
    this.notifications.push(...this.comments);
    console.log(this.notifications);
    this.notifications.sort((a:notification,b:notification) =>
     (new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime())
    );
    this.dataLoaded = true;
  }

  onAccept(fr: Friends) {
    fr.state = 'completed';
    this.service.saveFriendship(fr).subscribe(data => console.log(data));
  }

  onDecline(fr: Friends) {
    fr.state = 'deleted';
    this.service.deleteFriendship(fr.id).subscribe(data => console.log(data));
  }

  isJobLike(n: notification): n is JobLike {
    return (<JobLike>n).job !== undefined;
  }

  isPostLike(n: notification): n is Likes {
    return ((<Likes>n).post !== undefined) && ((<Comment>n).comment_text === undefined);
  }

  isComment(n: notification): n is Comment {
    return (<Comment>n).comment_text !== undefined;
  }

  getComment(n: notification): Comment{
    return <Comment>n;
  }

  getPostLike(n: notification): Likes{
    return <Likes>n;
  }

  getJobLike(n: notification): JobLike{
    return <JobLike>n;
  }

  local(d: Date): string{
    return new Date(d).toLocaleString();
  }

}

