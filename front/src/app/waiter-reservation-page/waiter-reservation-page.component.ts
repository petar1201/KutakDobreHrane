import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation.service';
import { ReservationArchive } from '../types/reservationArchive';
import { ReservationDto } from '../types/reservationDto';
import { UserServiceService } from '../services/user-service.service';
import { Restaurant } from '../types/restaurant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-waiter-reservation-page',
  templateUrl: './waiter-reservation-page.component.html',
  styleUrls: ['./waiter-reservation-page.component.css']
})
export class WaiterReservationPageComponent  implements OnInit{


  constructor(
    private reservationService: ReservationService,
    private userService: UserServiceService,
    private router: Router
  ){}

  reservations: ReservationDto[] = [];

  accepted: ReservationDto[] = [];

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

    this.reservationService.getAllAcceptedForWaiter(JSON.parse(localStorage.getItem("logged")).id).subscribe(
      res => {
        this.accepted = res as ReservationDto[]
      }
    )

 
  }

  formatDate(dateString: string): string {
  const date = new Date(dateString);
  date.setSeconds(0);
  date.setMilliseconds(0);
  date.setHours(date.getHours()+2)

  return date.toISOString().slice(0, 16).replace('T', ' ');
}

  moreFun(reservation){
    localStorage.setItem("reservation", JSON.stringify(reservation))
    this.router.navigate(["reservation"])
  }

  confirm(res: ReservationDto){
    this.reservationService.confirmShowUp(res.id).subscribe(
      res => {
        this.ngOnInit()
      }
    )
  }

  falseConfirm(res:ReservationDto){
    this.reservationService.confirmNotShowingUp(res.id).subscribe(
      res => {
        this.ngOnInit()
      }
    )
  }

}
