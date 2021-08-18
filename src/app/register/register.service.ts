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
  private usersUrl = 'https://localhost:8443/users';
  private imagesUrl = 'https://localhost:8443/images/upload/user';

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

  postImage(selectedFile: File, userid: number): Observable<Object>{
    console.log(selectedFile);
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);
    //Make a call to the Spring Boot Application to save the image
    return this.http.post(this.imagesUrl+'/'+userid, uploadImageData);
  }
}
