import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Message} from "../model/message";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private messagesUrl = 'http://localhost:8080/messages';
  private usersUrl = 'http://localhost:8080/users';
  m:Message;
  constructor( private http: HttpClient ) { }

  getMessages(userId:number): Observable<Message[]> {
    return this.http.get<Message[]>(this.messagesUrl+ '/' + userId);
  }

  sendMessage(senderId:number,receiverId:number,text:string): Observable<Message>{
    //console.log("sending message by: "+ senderId + " to " + receiverId);
    this.m=new Message();
    this.m.text=text;
    this.m.sender_id=senderId;
    this.m.receiver_id=receiverId;
    let dateTime=new Date();
    this.m.date_time=dateTime.getTime();
    return this.http.post<Message>(this.messagesUrl,this.m);
  }

  getUser(id:number): Observable<User> {
    const url = this.usersUrl + '/getbyid/' + id;
    return this.http.get<User>(url);
  }

}
