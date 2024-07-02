import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateReservationRequest } from '../types/createReservationRequest';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  createReservation(data: CreateReservationRequest){
    return this.http.post("http://localhost:8080/api/v1/reservation/create", data)
  }

  getArchive(idGuest: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/archive/"+idGuest)
  }

  getReservationsForRestaurant(idRestaurant: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/created/restaurant/"+idRestaurant)
  }


}
