import { Injectable } from '@angular/core';
import {User} from '../model/user';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Router }from '@angular/router';


const httpOptions = {
  headers: new HttpHeaders({'Accept': 'application/json', 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private usersUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient,private router: Router) {
  }


  getUser(un: string): Observable<User> {
    const url = this.usersUrl + '/' + un;
    return this.http.get<User>(url);
  }

  userloggedin(): void {
    this.router.navigate(['../home']);
  }

  adminloggedin(): void {
    this.router.navigate(['../admin']);
  }
}
