import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {Post} from "../model/post";
import {HomepageService} from "./homepage.service";
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SharedService} from "../shared.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {
  loading = false;
  requiredcondition:boolean;
  uploadcondition:boolean;
  currentuser:string;

  postForm = this.formBuilder.group({
    post_text: ''
  });

  constructor(private service: HomepageService,
              private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router) { this.requiredcondition = false; this.uploadcondition=false;}

  ngOnInit(): void {
    this.sharedService.curr_user.subscribe(user => this.currentuser=user);
  }

  submitPost(): void {
    this.loading=true;
    if(this.postForm.value.post_text==="" || this.postForm.value.post_text===null) {
      this.requiredcondition = true;
      this.postForm.reset();
    }
    else
      this.service.saveNewPost(this.postForm.value.post_text,this.currentuser).subscribe(data=>this.uploadcondition=true);
  }
}
