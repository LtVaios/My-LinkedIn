import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Login} from './model/login';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    login(username: string, password: string): Observable<HttpResponse<string>> {
        const ln: Login = { username, password };
        return this.http.post<string>('https://localhost:8443/login', ln, { observe: 'response'});
    }

    logout(): void {
        // remove token from local storage to log user out
        localStorage.removeItem('token');
    }
}
