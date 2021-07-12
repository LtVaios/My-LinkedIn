import { Injectable } from '@angular/core';
import {User} from '../model/user';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = 'http://localhost:8080/users';
  constructor( private http: HttpClient ) { }

  getUsers(): Observable<User[]> {
    // return USERS;
    return this.http.get<User[]>(this.usersUrl);
  }
  getUser(id:string): Observable<User> {
    const url = this.usersUrl + '/' + id;
    return this.http.get<User>(url);
  }

}
