import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-waiter-top-bar',
  templateUrl: './waiter-top-bar.component.html',
  styleUrls: ['./waiter-top-bar.component.css']
})
export class WaiterTopBarComponent implements OnInit{
  ngOnInit(): void {
  }

  constructor(private router:Router){}


  goToProfile(){
    this.router.navigate(['/waiter/profile']);
  }

  goToDeliveries(){
    this.router.navigate(['/waiter/delivery']);
  }

  goToReservations(){
    this.router.navigate(['/waiter/reservations']);
  }

  goToStatistics(){
    this.router.navigate(['/waiter/stats']);
  }

  logout(){
    localStorage.setItem('logged', "");
    this.router.navigate(['/']);
  }
}
