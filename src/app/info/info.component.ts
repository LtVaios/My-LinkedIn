import { Component, OnInit } from '@angular/core';
import {SharedService} from "../shared.service";
import {InfoService} from "../info/info.service";
import {User} from "../model/user";

import {tap} from "rxjs/operators";
import {colors} from "@angular/cli/utilities/color";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {
  data_loaded:boolean;
  currentUser: number;
  saved: boolean;
  user: User;
  work_experience: String;
  education: String;
  skills: String;
  userimage: any;
  changeImage: boolean;
  selectedFile: File;

  constructor(private sharedService: SharedService,
              private service: InfoService) {
    this.changeImage=false;
  }

  async ngOnInit() {
    this.data_loaded=false;
    this.currentUser=parseInt(<string>localStorage.getItem('currentuser'))
    this.saved = false;
    await this.service.getUser(this.currentUser).toPromise().then((response) => this.user = response);
    this.work_experience = this.user.work_experience;
    this.education = this.user.education;
    this.skills = this.user.skills;
    // console.log("USER " + this.user.work_experience);
    if (this.user.img!==null)
      this.userimage = 'data:image/jpeg;base64,' + this.user.img.picByte;

    this.data_loaded=true;
  }

  public onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  async onUpload() {
    await this.service.putImage(this.selectedFile, this.user.id).toPromise().then(response => console.log(response));
    await this.service.getUser(this.currentUser).toPromise().then(response => this.user = response);
    if (this.user.img!==null)
      this.userimage = 'data:image/jpeg;base64,' + this.user.img.picByte;
    this.changeImage = false;
  }
}
