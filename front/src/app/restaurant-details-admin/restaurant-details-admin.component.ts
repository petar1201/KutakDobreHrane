import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../types/restaurant';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { AdminServiceService } from '../services/admin-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { RestaurantService } from '../services/restaurant.service';
import { Observable, catchError } from 'rxjs';

@Component({
  selector: 'app-restaurant-details-admin',
  templateUrl: './restaurant-details-admin.component.html',
  styleUrls: ['./restaurant-details-admin.component.css']
})
export class RestaurantDetailsAdminComponent implements OnInit{

  restaurant: Restaurant;
  editMode: boolean = false;
  editedUser: Restaurant; // Optional: Use this to track changes locally before saving
  workingHours: string = ''; // Track new password separately
  errorMesage: string = "";

  
  constructor(
    private router: Router, 
    private userService: UserServiceService, 
    private adminService: AdminServiceService, 
    private restaurantService: RestaurantService,
    private route: ActivatedRoute

  ){
    this.handleUpdateError = this.handleUpdateError.bind(this);

  }

  private handleUpdateError(error: HttpErrorResponse){
    console.log("Update Failed")
    this.errorMesage = "Update Failed!"
    return new Observable;
  }


  ngOnInit(): void {
    this.restaurant = JSON.parse(localStorage.getItem("res_details"));
    // Optional: Make a copy of user object to track changes locally before saving
    // this.editedUser = { ...this.user };
  }

  toggleEditMode(): void {
    this.editMode = true;
    // Optional: Initialize editedUser with a copy of user object
    // this.editedUser = { ...this.user };
  }

  saveChanges(): void {
    console.log(this.restaurant);
      // Update user data in backend using userService or adminService
      // Example: this.userService.updateUser(this.editedUser).subscribe(...);
      // Optional: Update local user object with editedUser
      // this.user = { ...this.editedUser };
    this.restaurantService.setWorkingHours(this.restaurant.workingHours, this.restaurant.id)
    .pipe(
      catchError(this.handleUpdateError)
    ).subscribe(
      response => {
        localStorage.setItem("res_details", JSON.stringify(response))
        this.restaurant = response as Restaurant
      }
    )
    this.editMode = false;
  }

  cancelEdit(): void {
    this.editMode = false;
    // Optional: Reset editedUser to original user object
    // this.editedUser = { ...this.user };
  }

  goBack(): void {
    this.router.navigate([localStorage.getItem("back_route")]);
  }
}
