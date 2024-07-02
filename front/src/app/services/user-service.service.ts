import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs';
import { User } from '../types/user';
import UserType from '../types/userType';


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

  updateUser(user: User){
    let headers = new HttpHeaders()
    if(user.type === UserType.WAITER){
      headers = new HttpHeaders().set('idRes', user.idRes.toString());
    }
    return this.http.post("http://localhost:8080/api/v1/user/update", user, {headers})
  }

  getAll(){
    return this.http.get("http://localhost:8080/api/v1/user/all")
  }

  resetPassword(username: String, oldPass: String, newPass: String){
    const data = {}
    data["username"] = username
    data["oldPassword"] = oldPass
    data["newPassword"] = newPass
    return this.http.post("http://localhost:8080/api/v1/user/changePassword", data)
  }

  findRestaurant(idUser: Number){
    
      return this.http.get("http://localhost:8080/api/v1/user/restaurant/" + idUser)
  }

  findRestaurantDetails(idUser: Number){
    
    return this.http.get("http://localhost:8080/api/v1/user/restaurant/details/" + idUser)
}



  getCountUsers(){
    return this.http.get("http://localhost:8080/api/v1/user/count/registered")
  }

  getReservationSummary(){
    return this.http.get("http://localhost:8080/api/v1/reservation/summary")
  }

  setUserProfilePhoto(formData: FormData){
    return this.http.post("http://localhost:8080/api/v1/user/photo", formData)

  }



}
