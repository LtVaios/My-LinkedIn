import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {UserService} from './user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: User[];

  constructor(private service: UserService) {
    this.users=[];
  }

  ngOnInit() {
    this.service.getUsers().subscribe( users => this.users = users );
  }

}
