import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class InfoService {
  private usersUrl = 'http://localhost:8080/users';
  private imagesUrl = 'http://localhost:8080/images/upload/user';
  constructor(private http: HttpClient,private router: Router) { }

  getUser(user: number): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/getbyid/"+user);
  }

  putImage(selectedFile: File, userid: number): Observable<Object>{
    console.log(selectedFile);
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);
    //Make a call to the Spring Boot Application to save the image
    return this.http.put(this.imagesUrl+'/'+userid, uploadImageData);
  }
}
