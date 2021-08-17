import { Component, Input,OnInit } from '@angular/core';
import {SharedService} from "../../shared.service";
import {User} from "../../model/user";
import {SearchService} from "./search.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchData:string;
  users: User[];
  dataLoaded:boolean;
  empty:boolean;
  users_images: Map<User,string>;

  constructor(private sharedService: SharedService,private service: SearchService) {
    this.users_images = new Map<User, string>();
  }

  async ngOnInit() {
    this.dataLoaded=false;
    this.empty=false;
    //await new Promise(f => setTimeout(f, 5000));
    this.sharedService.curr_search.subscribe(response=>this.searchData=response)
    await this.service.searchUsers(this.searchData).toPromise().then(response => this.users=response);
    console.log(this.users);
    if(this.users.length==0)
      this.empty=true;
    this.dataLoaded=true

    for (var user of this.users){
      if (user.img!==null) {
        this.users_images.set(user,'data:image/jpeg;base64,' + user.img.picByte);
      }
    }
  }

}
