import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../services/reservation.service';
import { ReservationArchive } from '../types/reservationArchive';

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

  ngOnInit(): void {
      this.reservationService.getArchive(JSON.parse(localStorage.getItem("logged")).id).subscribe(
        res =>{
          this.archive = res as ReservationArchive
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
