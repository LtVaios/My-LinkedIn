import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {ChatService} from "./chat.service";
import {Message} from '../model/message';
import {User} from "../model/user";
import {FormBuilder} from "@angular/forms";
import {MywebService} from "../myweb/myweb.service";
import {Friends} from "../model/friends";
import {ActivatedRoute, Router} from "@angular/router";
import {Image} from "../model/image";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  dataLoaded:boolean;
  sending:boolean;
  messagesLoaded:boolean;
  currentUser:number;
  user:User;
  all_messages: Message[];
  load_messages: [string,boolean][];
  friends_: Friends[];
  chat_users:User[];
  users_images: Map<User, string>;

  messageForm = this.formBuilder.group({
    message_text: ''
  });

  constructor(private formBuilder: FormBuilder,
              private sharedService: SharedService,
              private webService: MywebService,
              private service: ChatService,
              private route: ActivatedRoute,
              private router: Router) {
    this.friends_=[];
    this.chat_users=[];
    this.load_messages=[];
    this.users_images = new Map<User, any>();
  }

  async ngOnInit() {
    this.dataLoaded=false;
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    await this.service.getMessages(this.currentUser).toPromise().then(response => this.all_messages=response);
    await this.webService.getFriends(this.currentUser).toPromise().then(friend => this.friends_=friend);
    for(let friend of this.friends_){
      if(friend.user_one==this.currentUser)
        await this.service.getUser(friend.user_two).toPromise().then(user=> {
          this.chat_users.push(user);
          if (user.img!==null) {
            this.users_images.set(user,'data:image/jpeg;base64,' + user.img.picByte);
          }
        });
      else
        await this.service.getUser(friend.user_one).toPromise().then(user=> {
          this.chat_users.push(user);
          if (user.img!==null) {
            this.users_images.set(user,'data:image/jpeg;base64,' + user.img.picByte);
          }
        });
    }
    this.user=this.chat_users[0];
    if(!(this.chat_users.length==0))
      await this.openChat(this.chat_users[0].id);
    else{
      window.alert("You have no friends")
      this.router.navigate(['../home']);
    }
    this.dataLoaded=true;
  }

  async openChat(id:number){
    this.messagesLoaded=false;
    var current_messages:Message[];

    for(let u of this.chat_users){
      if(u.id==id)
        this.user=u;
    }

    this.load_messages=[];
    current_messages=[];

    //refreshing all messages
    await this.service.getMessages(this.currentUser).toPromise().then(response => this.all_messages=response);

    //getting all the messages we have with that user out of all our messages
    for(let m of this.all_messages){
      if(m.sender_id==id || m.receiver_id==id)
        current_messages.push(m)
    }

    //sorting those messages by date and time
    current_messages.sort((a,b)=>a.date_time-b.date_time)

    //making a list of tuples that indicates if a message was sent by us or by the other person
    //so we use that information in the html template
    for(let m of current_messages){
      if(m.sender_id==this.currentUser)
        this.load_messages.push([m.text,true]);
      else
        this.load_messages.push([m.text,false]);
    }
    this.messagesLoaded=true;
  }


  async Send(){
    if(this.messageForm.value.message_text==="" || this.messageForm.value.message_text===null)
      this.messageForm.reset();
    else{
      await this.service.sendMessage(this.currentUser,this.user.id,this.messageForm.value.message_text).subscribe();
      this.sending=true;
      await new Promise(f => setTimeout(f, 2000));
      this.sending=false;
      this.messageForm.reset();
      this.openChat(this.user.id);
    }
  }

}
