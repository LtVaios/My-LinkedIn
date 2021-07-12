import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  //Default user exw valei ton bilbo gia pio eukolo testing ( kanonika prepei na einai _EMPTY_ kai na pairnei thn timh tou meta to login)
  private userSource=new BehaviorSubject<string>("bilbo@gmail.com");
  curr_user=this.userSource.asObservable();

  constructor() { }

  changeUser(username:string):void{
    this.userSource.next(username);
  }
}
