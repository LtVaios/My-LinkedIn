import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Video} from "../../model/video";

@Injectable({
  providedIn: 'root'
})
export class MultiUploadService {
  private videoUrl = 'https://localhost:8443/videos'
  private baseUrl = 'https://localhost:8443/images';
  private audUrl = 'https://localhost:8443/audio';

  constructor(private http: HttpClient) { }

  uploadImage(file: File, post_id:number): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('imageFile', file, file.name);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload/post/`+post_id, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  uploadAudio(file: File, post_id:number): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('audioFile', file, file.name);
    const req = new HttpRequest('POST', `${this.audUrl}/upload/post/`+post_id, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  uploadVideo(file: File, post_id:number): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('videoFile', file, file.name);

    const req = new HttpRequest('POST', `${this.videoUrl}/upload/`+post_id, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  downloadVideo(vid: Video): Observable<any> {
    return this.http.get(`${this.videoUrl}/download/`+vid.id , {
      reportProgress:true,
      observe: 'events',
      responseType: 'blob'
    })
  }

  // getFiles(post_id:number): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/post/`+post_id);
  // }
}
