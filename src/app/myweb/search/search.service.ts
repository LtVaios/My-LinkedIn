import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private usersUrl = 'http://localhost:8080/users';

  constructor( private http: HttpClient ) { }

  searchUsers(data:string): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl+ '/getbyfullname/' + data);
  }
}
