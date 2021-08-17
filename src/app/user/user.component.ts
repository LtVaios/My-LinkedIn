import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from './user.service'
import {SharedService} from "../shared.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  currentuser: User
  users: User[] | undefined;
  constructor(private userService: UserService,private shared_service: SharedService,){}

  async ngOnInit() {
    //localStorage.removeItem('token');
    // this.users = this.userService.getUsers();
    //await this.userService.getUser("bilbo@gmail.com").toPromise().then(user => this.currentuser = user);
    //this.shared_service.changeUser(this.currentuser.id);
    //this.shared_service.editUser("bilbo@gmail.com")
    this.getUsers();
  }

  async getUsers() {
    await this.userService.getUsers().toPromise().then(users => this.users = users);
  }


}
