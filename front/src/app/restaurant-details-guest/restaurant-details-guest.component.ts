import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../types/restaurant';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { AdminServiceService } from '../services/admin-service.service';
import { RestaurantService } from '../services/restaurant.service';
import { Observable, catchError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ReservationService } from '../services/reservation.service';
import { CreateReservationRequest } from '../types/createReservationRequest';
import { FoodDto } from '../types/foodDto';
import { FoodService } from '../services/food.service';
import { DeliveryService } from '../services/delivery.service';

@Component({
  selector: 'app-restaurant-details-guest',
  templateUrl: './restaurant-details-guest.component.html',
  styleUrls: ['./restaurant-details-guest.component.css']
})
export class RestaurantDetailsGuestComponent implements OnInit{

  restaurant: Restaurant;
  editMode: boolean = false;
  editedUser: Restaurant; // Optional: Use this to track changes locally before saving
  workingHours: string = ''; // Track new password separately
  errorMesage: string = "";
  reservationMessage: string = "";
  additionalRequests: string;
  numberOfPeople: number;
  reservationDate: string;

  cartString: String =""
  cartPrice: Number = 0

  quantity: number;

  menu: FoodDto[];
  cart: { food: FoodDto, quantity: number }[] = [];

  showMenu: boolean = false; // Track menu visibility
  showCart: boolean = false;


  
  constructor(
    private router: Router, 
    private userService: UserServiceService, 
    private adminService: AdminServiceService, 
    private restaurantService: RestaurantService,
    private reservationService: ReservationService,
    private foodService: FoodService,
    private deliveryService: DeliveryService,
    private route: ActivatedRoute

  ){
    this.handleUpdateError = this.handleUpdateError.bind(this);
    this.handleCreateReservationError = this.handleCreateReservationError.bind(this);

  }

  private handleCreateReservationError(error: HttpErrorResponse){
    switch(error.status){
        case 301:
          this.reservationMessage = "User not found"
          break;
        case 302:
          this.reservationMessage = "Restaurant not working"
          break;
        case 303:
          console.log("Restaurant not found")
          break;
        case 304:
          this.reservationMessage = "Table already booked"
          break;
        default:
          this.reservationMessage = "Default error"
    }
    return new Observable;
  }


  private handleUpdateError(error: HttpErrorResponse){
    console.log("Update Failed")
    this.errorMesage = "Update Failed!"
    return new Observable;
  }


  ngOnInit(): void {
    this.restaurant = JSON.parse(localStorage.getItem("res_details"));
    this.foodService.getMenu(this.restaurant.id).subscribe(
      res =>{
        this.menu = res as FoodDto[]
      }
    );
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


  makeReservation(): void {
    const reservationInstant = this.convertToInstant(this.reservationDate);

    let data: CreateReservationRequest = new CreateReservationRequest()
    data.dateTime = reservationInstant
    data.description = this.additionalRequests
    data.numOfPersons = this.numberOfPeople
    data.idGuest = JSON.parse(localStorage.getItem("logged")).id
    data.idRestaurant = this.restaurant.id


    
    this.reservationService.createReservation(data).pipe(
      catchError(this.handleCreateReservationError)
    ).subscribe(
      ret =>{
        this.reservationMessage = "Reseravation Created"
        this.additionalRequests = ""
        this.numberOfPeople = 0
        this.reservationDate = ""
      }
    )
    
  }
  private convertToInstant(dateTimeString: string): string {
    const date = new Date(dateTimeString);
    return date.toISOString();
  }

  toggleMenu(): void {
    this.showMenu = !this.showMenu; // Toggle menu visibility
  }

  addToCart(food: FoodDto): void {
    if(this.cartString===""){
      this.cartString = food.name + " x " + food.quantity
    }
    else{
      this.cartString = this.cartString + ", " + food.name + " x " + food.quantity
    }
    this.cartPrice =  (this.cartPrice as number) + (food.price as number) * food.quantity
    if (food.quantity > 0) {
      this.cart.push({ food: food, quantity: food.quantity });
      // Optionally, you can reset quantity to 1 after adding to cart
      food.quantity = 0;
    }
    console.log(this.cart)
  }


  toggleCart(): void {
    this.showCart = !this.showCart; // Toggle cart visibility
    // Ensure menu is hidden when cart is shown
    this.showMenu = false;
  }

  finishOrder(): void {
    console.log(this.cartString)
    this.cartString = this.cartString + " : " + this.cartPrice + "RSD"
    console.log(this.cartString)

    // Perform any necessary actions here, e.g., logging the order
    this.deliveryService.createDelivery(JSON.parse(localStorage.getItem("logged")).id, this.restaurant.id, this.cartString).subscribe(
      res =>{
        this.cartString =""
        this.cartPrice = 0
        console.log("Finishing order:", this.cart);
        this.cart = [];
      }
    )
    // Empty the cart after finishing
  }

}
