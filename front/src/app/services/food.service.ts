import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private httpClient: HttpClient) { }

  getMenu(idRes: Number){
      return this.httpClient.get("http://localhost:8080/api/v1/food/all/restaurant/"+idRes)
  }

}
