import { Injectable } from '@angular/core';
import {Friends} from '../model/friends';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class MywebService {

  private friendsUrl = 'https://localhost:8443/friends';
  private usersUrl = 'https://localhost:8443/users';
  constructor( private http: HttpClient ) { }

  getFriends(id:number): Observable<Friends[]> {
    return this.http.get<Friends[]>(this.friendsUrl+ '/' + id);
  }

  getUser(id:number): Observable<User> {
    const url = this.usersUrl + '/getbyid/' + id;
    return this.http.get<User>(url);
  }

}
