import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  constructor(private httpClient: HttpClient) { }

  createDelivery(idGuest: Number, idRestaurant:Number, cartString: String){
    const data = {
      "idGuest":idGuest,
      "idRestaurant":idRestaurant,
      "cart": cartString
    }
    return this.httpClient.post("http://localhost:8080/api/v1/delivery/create", data)
  }

  findActiveDeliveriesForGuest(idGuest: Number){
    return this.httpClient.get("http://localhost:8080/api/v1/delivery/guest/"+idGuest)
  }

  findDeliveriesForRestaurant(idRestaurant: Number){
    return this.httpClient.get("http://localhost:8080/api/v1/delivery/restaurant/"+idRestaurant)
  }

  accept(idDelivery: Number, deliveryTime: Number){
    const data = {
      "idDelivery":idDelivery,
      "deliveryTime": deliveryTime
    }

    return this.httpClient.post("http://localhost:8080/api/v1/delivery/confirm", data)

  }

  refuse(idDelivery: Number){
    return this.httpClient.get("http://localhost:8080/api/v1/delivery/refuse/"+idDelivery)

  }

}
