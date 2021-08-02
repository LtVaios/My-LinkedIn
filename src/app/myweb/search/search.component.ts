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
  constructor(private sharedService: SharedService,private service: SearchService) { }

  async ngOnInit() {
    this.dataLoaded=false;
    this.empty=false;
    //await new Promise(f => setTimeout(f, 5000));
    this.sharedService.curr_search.subscribe(response=>this.searchData=response)
    await this.service.searchUsers(this.searchData).toPromise().then(response => this.users=response);
    console.log(this.users);
    if(this.users.length==0)
      this.empty=true;
    this.dataLoaded=true;
  }

}
