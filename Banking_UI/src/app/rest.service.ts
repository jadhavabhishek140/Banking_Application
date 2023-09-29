import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  URL = "http://localhost:1111/";

  constructor(private http:HttpClient) { }

  checkStatus(data: any){
    return this.http.post(this.URL + "auth/valid/cust", data);
  }

  registerUser(data: any){
    return this.http.post(this.URL + "auth/user/register", data);
  }

  loginUser(data: any){
    return this.http.post(this.URL + "auth/user/login", data);
  }

}
