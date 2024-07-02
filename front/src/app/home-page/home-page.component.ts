import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { RestaurantService } from '../services/restaurant.service';
import { ReservationSummary } from '../types/reservationSummary';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{

  constructor(private router:Router, private userService: UserServiceService, private restaurantService:RestaurantService){}
  ngOnInit(): void {
    this.restaurantService.getCountRestaurants().subscribe(
      res => {
        this.countRestaurants = res as Number
      }
    )
    this.userService.getCountUsers().subscribe(
      res =>{
        this.countRegisteredUsers = res as Number
      }
    )
    this.userService.getReservationSummary().subscribe(
      res => {
        const dat = res as ReservationSummary
        this.lastDay = dat.lastDay
        this.lastWeek = dat.lastWeek
        this.lastMonth = dat.lastMonth
      }
    )
  }

  countRestaurants :Number = 0;
  countRegisteredUsers: Number = 0;
  lastDay: Number = 0;
  lastWeek: Number = 0;
  lastMonth: Number = 0;
  
  navigateToLogin(){
    this.router.navigate(['/login']);
  }

  shouldShow(){
    if(localStorage.getItem("res") || localStorage.getItem("reg")){
      if(localStorage.getItem("res") !== null){
        if(localStorage.getItem("res") !== "null"){
          return false  
        }
      }
       if(localStorage.getItem("reg") !== null){
        if(localStorage.getItem("reg") !== "null"){
          return false
        }
      }
    }
    return true;
  }
}
