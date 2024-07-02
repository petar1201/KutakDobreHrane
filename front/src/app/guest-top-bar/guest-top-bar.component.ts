import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-guest-top-bar',
  templateUrl: './guest-top-bar.component.html',
  styleUrls: ['./guest-top-bar.component.css']
})
export class GuestTopBarComponent implements OnInit{
  ngOnInit(): void {
  }

  constructor(private router:Router){}


  goToProfile(){
    this.router.navigate(['/guest/profile']);
  }

  goToRestaurants(){
    this.router.navigate(['/guest/restaurants']);
  }

  goToReservations(){
    this.router.navigate(['/guest/reservations']);
  }

  goToDeliveries(){
    this.router.navigate(['/guest/delivery']);
  }

  logout(){
    localStorage.setItem('logged', "");
    this.router.navigate(['/']);
  }
}
