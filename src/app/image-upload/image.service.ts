import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Friends} from "../model/friends";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private imagesUrl = 'http://localhost:8080/image/upload'
  constructor(private http: HttpClient) {}


  public uploadImage(image: File, name: string): Observable<Response> {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', image, name)
    //Make a call to the Spring Boot Application to save the image
    this.http.post('http://localhost:8080/image/upload', uploadImageData, { observe: 'response' });

    return this.http.post<Response>(this.imagesUrl, FormData);
  }
}
