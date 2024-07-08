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

  getTables(idReservation: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/tables/"+idReservation)
  }

  refuse(idReservation: Number, idWaiter: Number, description: String){
    const data = {
      "idReservation":idReservation,
      "idWaiter":idWaiter,
      "description":description
    }
    return this.http.post("http://localhost:8080/api/v1/reservation/decline", data)

  }

  accept(idReservation: Number, idWaiter: Number, tableNum: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/accept/"+idReservation+"/"+idWaiter+"/"+tableNum)
  }

  confirmShowUp(idReservation: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/show/confirm/true/"+idReservation)

  }
  confirmNotShowingUp(idReservation: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/show/confirm/false/"+idReservation)
  }

  getAllAcceptedForWaiter(idWaiter: Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/accepted/waiter/"+idWaiter)
  }

  histogram(){
    return this.http.get("http://localhost:8080/api/v1/reservation/histogram")
  }

  pie(idRes:Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/pie/"+idRes)
  }


  bar(idRes:Number){
    return this.http.get("http://localhost:8080/api/v1/reservation/bar/"+idRes)
  }
}
