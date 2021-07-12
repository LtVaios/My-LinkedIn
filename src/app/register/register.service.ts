import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../model/user";

const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class RegisterService {
  private usersUrl = 'http://localhost:8080/users';
  newuser: User;
  constructor(private http: HttpClient,private router: Router) { }

  getUser(id:string): Observable<User> {
    console.log("fetching user");
    const url = this.usersUrl + '/' + id;
    return this.http.get<User>(url);
  }


  userregister(fname:string,lname:string,uname:string,pass:string,phone:string): Observable<User> {
    console.log("saving new user");
    this.newuser=new User();
    this.newuser.admin=false;
    this.newuser.username=uname;
    this.newuser.phone=phone;
    this.newuser.firstName=fname;
    this.newuser.lastName=lname;
    this.newuser.password=pass;
    return this.http.post<User>(this.usersUrl,this.newuser);
  }
}
