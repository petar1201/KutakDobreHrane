import { CSP_NONCE, Component, OnInit } from '@angular/core';
import { DeliveryService } from '../services/delivery.service';
import { DeliveryDto } from '../types/deliveryDto';
import { UserServiceService } from '../services/user-service.service';
import { Restaurant } from '../types/restaurant';
import { MatDialog } from '@angular/material/dialog';
import { DeliveryTimeDialogComponent } from '../delivery-time-dialog/delivery-time-dialog.component';

@Component({
  selector: 'app-waiter-delivery-page',
  templateUrl: './waiter-delivery-page.component.html',
  styleUrls: ['./waiter-delivery-page.component.css']
})
export class WaiterDeliveryPageComponent implements OnInit{


  deliveries: DeliveryDto[];
  
  constructor(
    private deliveryService: DeliveryService,
    private userService: UserServiceService,
    private dialog: MatDialog
  ){}
  
    ngOnInit(): void {
      this.userService.findRestaurantDetails(JSON.parse(localStorage.getItem("logged")).id).subscribe(
        res =>{
          this.deliveryService.findDeliveriesForRestaurant(
            (res as Restaurant).id
          ).subscribe(
            ress =>{
              this.deliveries = ress as DeliveryDto[]
            }
          )
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
      return new Date(dateTime).toLocaleString();
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



    accept(delivery: any): void {
      const dialogRef = this.dialog.open(DeliveryTimeDialogComponent);
  
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.processDeliveryAcceptance(delivery, result);
        }
      });
    }
  
    processDeliveryAcceptance(delivery: any, time: string) {

      let ttime = 45

      if(time[0]==="2"){
        ttime = 25
      }
      else if(time[1]=="3"){
        ttime = 35
      }
      this.deliveryService.accept(delivery.id,ttime ).subscribe(
        res =>{
          this.ngOnInit()
        }
      )
    }

    refuse(delivery){
      this.deliveryService.refuse(delivery.id).subscribe(
        res =>{
          this.ngOnInit()
        }
      )
    }
  
  }
  