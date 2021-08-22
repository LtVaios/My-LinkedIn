import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MultiUploadService {
  private baseUrl = 'https://localhost:8443/images';
  private audUrl = 'https://localhost:8443/audio';

  constructor(private http: HttpClient) { }

  upload(file: File, post_id:number): Observable<HttpEvent<any>> {
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

  getFiles(post_id:number): Observable<any> {
    return this.http.get(`${this.baseUrl}/post/`+post_id);
  }
}
