import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs';
import { User } from '../types/user';


@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http: HttpClient) { }

  login(username_ : String, password_ : String){
    const data = {
      username : username_,
      password : password_
    }
    return this.http.post("http://localhost:8080/api/v1/user/login", data)
  }
  
  registerUser(user: User){
    return this.http.post("http://localhost:8080/api/v1/user/register", user)
  }

  


}
