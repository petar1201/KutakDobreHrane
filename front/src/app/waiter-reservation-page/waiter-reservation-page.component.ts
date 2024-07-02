import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation.service';
import { ReservationArchive } from '../types/reservationArchive';
import { ReservationDto } from '../types/reservationDto';
import { UserServiceService } from '../services/user-service.service';
import { Restaurant } from '../types/restaurant';

@Component({
  selector: 'app-waiter-reservation-page',
  templateUrl: './waiter-reservation-page.component.html',
  styleUrls: ['./waiter-reservation-page.component.css']
})
export class WaiterReservationPageComponent  implements OnInit{


  constructor(
    private reservationService: ReservationService,
    private userService: UserServiceService
  ){}

  reservations: ReservationDto[] = [];

  ngOnInit(): void {
    
    this.userService.findRestaurantDetails(JSON.parse(localStorage.getItem("logged")).id).subscribe(
      ress => {
        this.reservationService.getReservationsForRestaurant((ress as Restaurant).id).subscribe(
          res =>{
            this.reservations = res as ReservationDto[]
          }
        )
      }
    )

 
  }

  formatDate(dateString: string): string {
  const date = new Date(dateString);
  date.setMinutes(0);
  date.setSeconds(0);
  date.setMilliseconds(0);
  return date.toISOString().slice(0, 16).replace('T', ' ');
}

}
