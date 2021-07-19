import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class EditService {
  private usersUrl = 'http://localhost:8080/users';
  constructor(private http: HttpClient,private router: Router) { }

  getUser(user: string): Observable<User>{
    return this.http.get<User>(this.usersUrl+"/"+user);
  }

  updateUser(username: string, user: User): Observable<User>{
    return this.http.put<User>(this.usersUrl+"/"+username, user);
  }
  // getWork_exp(user: string): Observable<User>{
  //   return this.http.get<User>(this.usersUrl+"/"+user);
  // }

  changeWorkexp(user: string, text: string): Observable<User>{
    return this.http.put<User>(this.usersUrl+"/"+user+"/workexp", text);
  }

  changeEducation(user: string, text: string): Observable<User>{
    return this.http.put<User>(this.usersUrl+"/"+user+"/education", text);
  }

  changeSkills(user: string, text: string): Observable<User>{
    return this.http.put<User>(this.usersUrl+"/"+user+"/skills", text);
  }
}
