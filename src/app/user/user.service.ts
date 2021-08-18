import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable()
export class UserService {

  private usersUrl = 'https://localhost:8443/users';

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  getUser(un: string): Observable<User> {
    const url = this.usersUrl + '/' + un;
    return this.http.get<User>(url);
  }

  constructor(private http: HttpClient) {}

}
