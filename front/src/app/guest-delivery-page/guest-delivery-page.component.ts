import { Component, OnInit } from '@angular/core';
import { DeliveryDto } from '../types/deliveryDto';
import { DeliveryService } from '../services/delivery.service';
import { getLocaleDateTimeFormat } from '@angular/common';

@Component({
  selector: 'app-guest-delivery-page',
  templateUrl: './guest-delivery-page.component.html',
  styleUrls: ['./guest-delivery-page.component.css']
})
export class GuestDeliveryPageComponent implements OnInit{


deliveries: DeliveryDto[];

constructor(
  private deliveryService: DeliveryService
){}

  ngOnInit(): void {
    this.deliveryService.findActiveDeliveriesForGuest(
      JSON.parse(localStorage.getItem("logged")).id
    ).subscribe(
      res =>{
        this.deliveries = res as DeliveryDto[]
      }
    )
  }

  getExpectedArrival(delivery: DeliveryDto): string {
    return delivery.expectedArrival ? delivery.expectedArrival.toString() : 'N/A';
  }
  isActiveDelivery(delivery: DeliveryDto): boolean {
    return delivery.status === 'ACCEPTED' || delivery.status === 'CREATED';
  }

  isDeliveredDelivery(delivery: DeliveryDto): boolean {
    return delivery.status === 'DELIVERED';
  }

  formatDateTime(dateTime: string): string {
    // Add your date formatting logic here
    let datee = new Date(dateTime)
    datee.setHours(datee.getHours()-2)
    return datee.toLocaleString();
  }

  isThereAnyActive(): boolean{
    let i = 0
    for(i; i<this.deliveries.length; i++){
      if(this.isActiveDelivery(this.deliveries[i])){
        return true;
      }
    }
    return false;
  }

  isThereAnyDelivered(): boolean{
    let i = 0
    for(i; i<this.deliveries.length; i++){
      if(this.isDeliveredDelivery(this.deliveries[i])){
        return true;
      }
    }
    return false;
  }

}
