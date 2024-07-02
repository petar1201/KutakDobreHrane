import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Restaurant } from '../types/restaurant';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private http: HttpClient) { }

  createRestaurant(data: Restaurant){
    return this.http.post("http://localhost:8080/api/v1/restaurant/create", data)
  }
  
  getAll(){
    return this.http.get("http://localhost:8080/api/v1/restaurant/all")
  }

  setWorkingHours(data: String, id: Number){
    return this.http.get("http://localhost:8080/api/v1/restaurant/update/hours/" + id + "/" + data)
  }
  

  getRestaurants(){
    return this.http.get("http://localhost:8080/api/v1/restaurant/all/waiters")
  }


  getCountRestaurants(){
    return this.http.get("http://localhost:8080/api/v1/restaurant/count")
  }

}
