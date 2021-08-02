import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  //Default user exw valei ton bilbo gia pio eukolo testing ( kanonika prepei na einai _EMPTY_ kai na pairnei thn timh tou meta to login)
  private userSource=new BehaviorSubject<number>(1);
  curr_user=this.userSource.asObservable();

  private searchSource=new BehaviorSubject<string>("1");
  curr_search=this.searchSource.asObservable();

  constructor() { }

  changeUser(id:number):void{
    this.userSource.next(id);
  }

  editSearch(str:string):void{
    this.searchSource.next(str);
  }
}
