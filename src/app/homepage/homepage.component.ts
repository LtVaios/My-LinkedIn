import { Component, OnInit } from '@angular/core';
import {Post} from "../model/post";
import {HomepageService} from "./homepage.service";
import {FormBuilder} from "@angular/forms";
import {SharedService} from "../shared.service";
import {Friends} from "../model/friends";
import {Likes} from "../model/likes";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {MultiUploadService} from "./multi-upload/multi-upload.service";
import {HttpErrorResponse, HttpEventType, HttpResponse} from "@angular/common/http";
import {Image} from "../model/image";
import {Video} from "../model/video";
import {DomSanitizer, SafeResourceUrl, SafeUrl} from "@angular/platform-browser";
import {Audio_} from "../model/audio";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {
  requiredcondition:boolean;
  loading:boolean;
  uploaded:boolean;
  aud:boolean;
  posts: Post[];
  liked_posts: Map<Post,boolean>;
  friends: Friends[];
  currentuser:number;
  likes_count: Map<Post, number>;
  videos: Map<number, SafeUrl>;
  urllist: string[];
  url: any;
  postForm = this.formBuilder.group({
    post_text: ''
  });
  fileUrl: SafeResourceUrl;

  constructor(private service: HomepageService,
              private sharedService: SharedService,
              private formBuilder: FormBuilder,
              private router: Router,
              private uploadService: MultiUploadService,
              private sanitizer : DomSanitizer) {
    this.requiredcondition = false;
    this.loading=false;
    this.likes_count = new Map<Post, number>();
    this.videos = new Map<number, SafeUrl>();
  }

  async ngOnInit(){
    this.currentuser=parseInt(<string>localStorage.getItem('currentuser'))
    this.loadPosts();
  }

  async loadPosts(){
    this.loading=true;
    let temp: Set<Post>;
    let temp_posts: Post[]=[];
    let likes:Likes[]=[];
    let temp_likes: Likes[]=[];
    let friend_likes:Likes[]=[];
    let likes_id:number[]=[];
    temp=new Set<Post>();
    this.liked_posts=new Map<Post, boolean>();
    this.posts=[];

    await this.service.getPosts(this.currentuser).toPromise().then(post=>this.posts=post);
    await this.service.getFriends(this.currentuser).toPromise().then(response=> this.friends=response);

    //adding our friends' posts into the temporary post list
    for (let f of this.friends) {
      if (f.user_one == this.currentuser) {
        await this.service.getPosts(f.user_two).toPromise().then(response => temp_posts = response);
        await this.service.getUserLikes(f.user_two).toPromise().then(response => temp_likes = response)
      }
      else {
        await this.service.getPosts(f.user_one).toPromise().then(response => temp_posts = response);
        await this.service.getUserLikes(f.user_one).toPromise().then(response => temp_likes = response);
      }
      for(let p of temp_posts)
          temp.add(p)
      for(let l of temp_likes)
          friend_likes.push(l)
    }

    //adding the posts that our friends liked in the temporary post list
    for (let l of friend_likes)
      temp.add(l.post)

    //adding our posts in the temporary post list
    for(let p of this.posts)
      temp.add(p)


    //This is an algorithm that removes duplicate posts from temp post list so we keep only 1 copy of each post to show at the user
    let dupl:number[]=[];
    let deleted:[number,number][]=[];
    let i:number;
    let flag:boolean;
    for(let p of temp){
      flag=false
      for (let d of deleted)
        if(d[0]==p.id)
          flag=true
      if(flag==true)
        continue
      if(!dupl.includes(p.id))
        dupl.push(p.id)
      else {
        i=0
        for(let p2 of temp)
          if(p2.id==p.id)
            i++
        deleted.push([p.id,i-1])
      }
    }
    for(let d of deleted)
      for(let p of temp)
        if(d[0]==p.id){
          if(d[1]>0) {
            temp.delete(p)
            d[1]--
          }
          else
            break
        }

    this.posts=[]
    //adding the posts with the boolean indicator aside that shows if the user has liked the current post
    //so the html button will be disabled (you cannot like the same post more than 1 times)
    for (let p of temp) {
      this.posts.push(p)
      likes_id=[];
      likes=[];
      await this.service.getPostLikes(p.id).toPromise().then(response=>likes=response);
      this.likes_count.set(p, likes.length);
      for(let l of likes)
        likes_id.push(l.user.id)
      if(likes_id.includes(this.currentuser))
        this.liked_posts.set(p, true);
      else
        this.liked_posts.set(p, false);

      for (let vid of p.videos) {
        this.uploadService.downloadVideo(vid).subscribe(
          event => {
            console.log(event);
            if (event.type === HttpEventType.UploadProgress) {
              // this.progressInfosV[idx].value = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              console.log(event.body);
              this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(event.body));
              const reader = new FileReader();
              reader.onload = (e: any) => {
                this.videos.set(vid.id, e.target.result);
              };
              reader.readAsDataURL(event.body);
            }
          },
          (error: HttpErrorResponse) => {
            console.log(error)
          }
        );
      }
    }
    this.loading=false;
  }

  async submitPost() {
    this.previews = [];
    this.loading=true;
    this.requiredcondition=false;
    this.uploaded=false;
    //checking if the post we try to upload is empty (you cannot upload an empty post)
    if(this.postForm.value.post_text==="" || this.postForm.value.post_text===null) {
      this.requiredcondition = true;
      this.loading=false;
      // this.postForm.reset();
    }
    else {
      this.service.saveNewPost(this.postForm.value.post_text, this.currentuser).subscribe(data => {
        console.log(data);
        this.uploadFiles(data.id);
        this.uploadFilesV(data.id);
      });
      this.previews = [];
      await new Promise(f => setTimeout(f, 2000));
      this.postForm.reset();
      this.previews = [];
      this.loading=false;
      this.uploaded=true;
    }
    await this.loadPosts();
  }

  async likePost(p:Post){
    let l:Likes;
    l=new Likes();
    await this.service.getUser(this.currentuser).toPromise().then(response=>l.user=response)
    l.post=p;
    l.createdDate = new Date();
    await this.service.saveLike(this.currentuser,l).toPromise().then(response=>console.log(response))
    this.liked_posts.set(p,true);
    var count: number|undefined = this.likes_count.get(p);
    if (count!=undefined)
      this.likes_count.set(p, count+1);
  }

  goToPost(id:number){
    this.router.navigate(['../posts/'+id]);
  }


  /* For pictures and audio files*/
  selectedImageFiles?: FileList;
  selectedAudioFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];
  previews: string[] = [];
  previewsA: string[] = [];

  selectImageFiles(event: any,aud:boolean): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedImageFiles = event.target.files;
    this.aud=aud

    this.previews = [];
    if (this.selectedImageFiles && this.selectedImageFiles[0]) {
      const numberOfFiles = this.selectedImageFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          //console.log(e.target.result);
          if(!this.aud)
            this.previews.push(e.target.result);
          else
            this.previewsA.push(e.target.result);
        };

        reader.readAsDataURL(this.selectedImageFiles[i]);
      }
    }
  }

  selectAudioFiles(event: any,aud:boolean): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedAudioFiles = event.target.files;
    this.aud=aud
    this.previewsA=[];
    if (this.selectedAudioFiles && this.selectedAudioFiles[0]) {
      const numberOfFiles = this.selectedAudioFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          //console.log(e.target.result);
          if(!this.aud)
            this.previews.push(e.target.result);
          else
            this.previewsA.push(e.target.result);
        };

        reader.readAsDataURL(this.selectedAudioFiles[i]);
      }
    }
  }

  uploadFiles(post_id: number): void {
    this.message = [];
    if (this.selectedImageFiles) {
      for (let i = 0; i < this.selectedImageFiles.length; i++) {
        this.upload(i, this.selectedImageFiles[i], post_id);
      }
    }
    if (this.selectedAudioFiles) {
      for (let i = 0; i < this.selectedAudioFiles.length; i++) {
        this.uploadAudio(i, this.selectedAudioFiles[i], post_id);
      }
    }
  }

  upload(idx: number, file: File, post_id: number): void {
    this.progressInfos[idx] = { value: 0, fileName: file.name };

    if (file) {
      this.uploadService.uploadImage(file, post_id).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.message.push(msg);
            // this.imageInfos = this.uploadService.getFiles();
          }
        },
        (err: any) => {
          this.progressInfos[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.message.push(msg);
        });
    }
  }

  uploadAudio(idx: number, file: File, post_id: number): void {
    this.progressInfos[idx] = { value: 0, fileName: file.name };

    if (file) {
      this.uploadService.uploadAudio(file, post_id).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.message.push(msg);
            // this.imageInfos = this.uploadService.getFiles();
          }
        },
        (err: any) => {
          this.progressInfos[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.message.push(msg);
        });
    }
  }

  imagesrc(img: Image): string{
    if (img==null) return "";
    else return 'data:image/jpeg;base64,'+img.picByte;
  }

  audiosrc(aud: Audio_): string{
    if (aud==null) return "";
    else return 'data:audio/mp3;base64,'+aud.audByte;
  }

  /* For videos */
  selectedFilesV?: FileList;
  progressInfosV: any[] = [];
  messageV: string[] = [];

  previewsV: string[] = [];
  imageInfosV?: Observable<any>;

  selectFilesV(event: any): void {
    this.messageV = [];
    this.progressInfosV = [];
    this.selectedFilesV = event.target.files;

    this.previewsV = [];
    if (this.selectedFilesV && this.selectedFilesV[0]) {
      const numberOfFiles = this.selectedFilesV.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          //console.log(e.target.result);
          this.previewsV.push(e.target.result);
        };

        reader.readAsDataURL(this.selectedFilesV[i]);
      }
    }
  }

  uploadFilesV(post_id: number): void {
    this.messageV = [];

    if (this.selectedFilesV) {
      for (let i = 0; i < this.selectedFilesV.length; i++) {
        this.uploadV(i, this.selectedFilesV[i], post_id);
      }
    }
  }

  uploadV(idx: number, file: File, post_id: number): void {
    this.progressInfosV[idx] = { value: 0, fileName: file.name };

    if (file) {
      this.uploadService.uploadVideo(file, post_id).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfosV[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.messageV.push(msg);
            // this.imageInfos = this.uploadService.getFiles();
          }
        },
        (err: any) => {
          this.progressInfosV[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.messageV.push(msg);
        });
    }
  }

  videosrc(vid: Video){
    return this.uploadService.downloadVideo(vid);
  }

}
