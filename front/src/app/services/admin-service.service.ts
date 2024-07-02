import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import UserStatus from '../types/userStatus';


@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {

  constructor(private http: HttpClient) { }

  login(username_ : String, password_ : String){
    const data = {
      username : username_,
      password : password_
    }
    return this.http.post("http://localhost:8080/api/v1/admin/login", data)
  }
  
  userConfirmation(idAdmin : Number, idUser : Number, password: String, status: UserStatus){
    const data = {
      "idAdmin" : idAdmin,
      "idUser" : idUser
    }
    return this.http.post("http://localhost:8080/api/v1/admin/userConfirmation/"+status, data)
  }

}
