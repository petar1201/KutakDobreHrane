import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation.service';
import { ReservationArchive } from '../types/reservationArchive';
import { ReservationDto } from '../types/reservationDto';

@Component({
  selector: 'app-guest-reservations-page',
  templateUrl: './guest-reservations-page.component.html',
  styleUrls: ['./guest-reservations-page.component.css']
})
export class GuestReservationsPageComponent implements OnInit{


  constructor(
    private reservationService: ReservationService
  ){}

  archive: ReservationArchive;
  expired: ReservationDto[]

  ngOnInit(): void {
      this.reservationService.getArchive(JSON.parse(localStorage.getItem("logged")).id).subscribe(
        res =>{
          this.archive = res as ReservationArchive
          this.expired = this.archive.expired
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

}
