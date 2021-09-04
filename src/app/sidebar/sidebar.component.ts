import { Component, OnInit } from '@angular/core';
import {InfoService} from "../info/info.service";
import {User} from "../model/user";
import {MywebService} from "../myweb/myweb.service";
import {Image} from "../model/image";
import {Friends} from "../model/friends";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  currentUser: number;
  user: User;
  // userloaded: boolean;
  web: User[];
  friends_: Friends[];

  constructor( private info_service: InfoService,
               private myweb_service: MywebService) {
    this.web=[];
    this.friends_=[];
  }

  async ngOnInit() {
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'));
    await this.getMyInfo();
    this.getMyWeb();
  }

  async getMyInfo(){
    await this.info_service.getUser(this.currentUser).toPromise().then(
      data=> {
        this.user = data;
        //console.log(this.user);
      }
    );
  }

  async getMyWeb() {
    await this.myweb_service.getFriends(this.currentUser).toPromise().then(friend => this.friends_=friend);
    for(let friend of this.friends_){
      if(friend.user_one==this.currentUser)
        await this.myweb_service.getUser(friend.user_two).toPromise().then(user=> {
          this.web.push(user);
        });
      else
        await this.myweb_service.getUser(friend.user_one).toPromise().then(user=> {
          this.web.push(user);
        });
    }
  }

  imagesrc(img: Image): string{
    if (img==null) return "";
    else return 'data:image/jpeg;base64,'+img.picByte;
  }
}
