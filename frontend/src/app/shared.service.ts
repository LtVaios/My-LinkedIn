import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private searchSource=new BehaviorSubject<string>("1");
  curr_search=this.searchSource.asObservable();

  constructor() { }


  editSearch(str:string):void{
    this.searchSource.next(str);
  }
}
