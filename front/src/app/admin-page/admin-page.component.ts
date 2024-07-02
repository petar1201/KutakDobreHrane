import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminServiceService } from '../services/admin-service.service';
import { Admin } from '../types/admin';
import { User } from '../types/user';
import { UserServiceService } from '../services/user-service.service';
import UserStatus from '../types/userStatus';
import { Observable, catchError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import UserType from '../types/userType';
import { Restaurant } from '../types/restaurant';
import { RestaurantService } from '../services/restaurant.service';
import RestaurantType from '../types/restaurantType';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit{

  ngOnInit(): void {
      this.admin = JSON.parse(localStorage.getItem("admin"))
      this.userService.getAll().subscribe(
        response => {
          this.users = response as User[]
          console.log(this.users)
        }
      )
      this.restaurantService.getAll().subscribe(
        response => {
          this.restaurants = response as Restaurant[]
          console.log(this.restaurants)
        }
      )
  }


  username: String;
  password: String;
  registerFlag: Boolean = false;
  securityQuestion: String;
  securityAnswer: String;
  firstName: String;
  lastName: String;
  gender: String = "";
  address: String;
  phoneNumber: String;
  email: String;
  type: UserType = UserType.WAITER;
  school: String = "";
  registerPorukaGreske: String = "";
  profilePhoto: String = "a";
  cardNumber: String = "";
  idRes: Number = null

  rName: String = "";
  rAddress: String = "";
  rContactPerson: String = "";
  rType: RestaurantType = RestaurantType.CHINESE;
  rRestaurantLayout: String = "";
  rDescription: String = "";


  restaurantPorukaGreske: String = "";
  restaurantFlag = false;


  admin : Admin;
  users: User[];
  restaurants: Restaurant[];

  constructor(private router: Router, private userService: UserServiceService, private adminService: AdminServiceService,private restaurantService: RestaurantService ,private route: ActivatedRoute){
    this.handleError = this.handleError.bind(this)
    this.handleRegisterError = this.handleRegisterError.bind(this)

  }

  private handleRegisterError(error: HttpErrorResponse){
    switch (error.status){
      case 302:
        this.registerPorukaGreske = "Student going to invalid grade!"
        console.log("Student going to invalid grade!")
        break;
      case 305:
        this.registerPorukaGreske = "Invalid Gender"
        console.log("Invalid Gender")
        break;
      case 301:
        this.registerPorukaGreske = "Password Invalid Format!"
        console.log("Password Invalid Format!")
        break;
      case 303:
        this.registerPorukaGreske = "Unknown error! Register Failed."
        console.log("Unknown error! Register Failed.")
        break;
      case 400:
        this.registerPorukaGreske = "Bad Params!"
        console.log("Bad params!")
        break;
      default:
        this.registerPorukaGreske = "Default error"
        console.log("Default error!")

    }
    return new Observable;
  }

  private handleCreateRestaurantError(error: HttpErrorResponse){
    this.restaurantService.getAll().subscribe(
      response => {
        this.restaurants = response as Restaurant[]
        console.log(this.restaurants)
      }
    )
    switch (error.status){
      case 301:
        this.restaurantPorukaGreske = "Invalid Layout!"
        console.log("Invalid Layout!")
        break;
      default:
        this.restaurantPorukaGreske = "Default error"
        console.log("Default error!")

    }
    return new Observable;
  }


  private handleError(error: HttpErrorResponse){
    this.userService.getAll().subscribe(
      response => {
        this.users = response as User[]
        console.log(this.users)
      }
    )
    return new Observable;
  }

  showDetails(user: User) {
    localStorage.setItem("user_details", JSON.stringify(user))
    console.log('Showing details for:', user);
    localStorage.setItem("back_route", "/admin")
    this.router.navigate(["/user_details"])
  }

  activateUser(user: User) {
    this.adminService.userConfirmation(this.admin.id, user.id, this.admin.password, UserStatus.ACTIVE ).pipe(
      catchError(this.handleError)
    ).subscribe(
      response =>{
        console.log('Activated user:', user);
        this.userService.getAll().subscribe(
          response => {
            this.users = response as User[]
            console.log(this.users)
          }
        )
      }
      
    )
  }
  deactivateUser(user: User) {
    this.adminService.userConfirmation(this.admin.id, user.id, this.admin.password, UserStatus.REFUSED ).pipe(
      catchError(this.handleError)
    ).subscribe(
      response =>{
        console.log('Deactivated user:', user);
        this.userService.getAll().subscribe(
          response => {
            this.users = response as User[]
            console.log(this.users)
          }
        )
      }
    )
  }

  registerUser(){
    let stud: User = new User();
    stud.username = this.username;
    stud.password = this.password;
    stud.securityQuestion = this.securityQuestion;
    stud.securityAnswer = this.securityAnswer;
    stud.name = this.firstName;
    stud.lastName = this.lastName;
    stud.gender = this.gender;
    stud.address = this.address;
    stud.phoneNumber = this.phoneNumber;
    stud.email = this.email;
    stud.cardNumber = this.cardNumber || ""; 
    stud.type = this.type;
    if (this.idRes != null){
      stud.idRes = this.idRes
    }

    if(this.src !== ""){
      stud.profilePhoto = this.src
    }
    else{
      stud.profilePhoto = "data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsBAMAAACLU5NGAAAAG1BMVEUAmf8fpf8/sv9fv/9/zP+f2P+/5f/f8v/////4L7EgAAACv0lEQVR42u3XsW/aQBiG8fcMJBmd0gRGQxWVkSZqxIiyNCPN0DJStUo9hqptPAaIzf3ZHYzxGYdMV6bnt/BIkaLT58uHIwEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPhnrq7CTb67rFdd++rDpoKrXq08ubDWrseSZKbWZmG16oKptfa3JOnMWvttpzxpZTehuc4kafDcM59W1aqb/uipHc8kBfYmfJsMK+VLM5Kk24lk1qFk4sitF9xIUjOV1J1LOl9Wyq+jJ+loIUmdB7f2iiXFoSTzWCm/mgtpMJGk5sqtvW5DBWleQ6c8ayykaX7HM7f2GoVqLSRJJzOnPGs9SY/bx1OWJDXzQzYqV20qHT8Uz78sz7oTKd0+nrJU3Gdp5M7CpNLJpHj+ZXneqI+hgufN4xmWlS+qdSipUXmknbk0GOdTXDnlVTD9Vv7SwbCszed8d1gXWVieeuWUR19+2jtJjc3a6Y7L0nZczrBa90kaSRrll808O+XzWPfJn+i1Y2kwd4fVuv+5vvv/x5LMdfrqsYJ1u7os2vHkAMeSRpPXjqVBsrOSGulBjtVcbi/sYBjsXHmpaXdfJ26j4sfByinfGyItF0RU1naY8by+6Iq1sHTKt2z/OpUaWb67HCezA6xT6e/+Lx9pNMt3l+N4dogvH6XFcEylNsMqVr0zrUkxnK5bvt8gVq+82Ixmxap3/nSHh3ixOV5IR0tJ6s7c0vbbcGdcSSjFkSSTyS1fzkJJJh5K5jGSTBK55RxvkH+Yz5J0vpLU+SWps6yUL53s8rQ9fZak92nvzXRZLUmNzarPp2WS773Tj3YsKUjuTi9sVClvrq21aX7Hv9brhWsYW2s/55NO6uXvtaa//c/zTb9edf1+cWLTj2oFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADg3z9bSadHng4eqAAAAABJRU5ErkJggg=="
    }

    this.userService.registerUser(stud).pipe(
        catchError(this.handleRegisterError)
    )
    .subscribe(
        response_ => {
          this.activateUser(response_ as User)
          this.registerPorukaGreske = ""
          this.registerFlag = false
          this.idRes = null;
          console.log(stud)
      }
    )
  }



  addWaiter(){
    this.registerPorukaGreske  = ""
    if (this.registerFlag){
      this.registerFlag = false
    }
    else{
      this.registerFlag = true
    }

  }


  addRestaurant(){
    this.restaurantPorukaGreske  = ""
    if (this.restaurantFlag){
      this.restaurantFlag = false
    }
    else{
      this.restaurantFlag = true
    }

  }


  logout(): void {
    // Implement your logout logic here
    localStorage.setItem('admin', ""); // Example: Remove admin data from localStorage
    this.router.navigate(['/']); // Example: Navigate to login page
  }


  createRestaurant(){
    let restaurant = new Restaurant();
    restaurant.name = this.rName
    restaurant.address = this.rAddress
    restaurant.contactPerson = this.rContactPerson
    restaurant.description = this.rDescription
    restaurant.type = this.rType
    restaurant.restaurantLayout = this.rRestaurantLayout
    console.log(restaurant)
    this.restaurantService.createRestaurant(restaurant).pipe(
        catchError(this.handleCreateRestaurantError)
    )
    .subscribe(
        response_ => {
          this.activateUser(response_ as User)
          this.restaurantPorukaGreske = ""
          this.restaurantFlag = false
          console.log(restaurant)
          this.restaurantService.getAll().subscribe(
            response => {
              this.restaurants = response as Restaurant[]
              console.log(this.restaurants)
            }
          )
          this.rName = "";
          this.rAddress = "";
          this.rContactPerson = "";
          this.rRestaurantLayout = "";
          this.rDescription = "";
      }
    )
    }

    showRestaurantDetails(restaurant){
      localStorage.setItem("res_details", JSON.stringify(restaurant))
      console.log('Showing details for:', restaurant);
      localStorage.setItem("back_route", "/admin")
      this.router.navigate(["/res_details_admin"])
    }

    
  selectedFile: File | null = null;
  isImageValid: boolean = true;
  previewUrl: string | ArrayBuffer | null = null;

  src = ""
  img = null

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const img = new Image();
        img.src = e.target.result;
        this.src = "";
        this.img = img
        console.log("EVO")
        console.log(img)
        img.onload = () => {
          const width = img.width;
          const height = img.height;
          const isSizeValid = width >= 100 && width <= 300 && height >= 100 && height <= 300;
          const isFormatValid = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg';
          this.isImageValid = isSizeValid && isFormatValid;
          if (this.isImageValid) {
            this.src = img.src;
            this.selectedFile = file;
            this.previewUrl = e.target.result;
            this.registerPorukaGreske = ""
          } else {
            if(!isSizeValid){
              this.registerPorukaGreske = "Slika nije u validnom formatu(100x100--300x300)"
            }
            else if(!isFormatValid){
              this.registerPorukaGreske = "Slika nije u validnom formatu(jpg, png)"
            }
            if(!isSizeValid && !isFormatValid){
              this.registerPorukaGreske = "Slika nije u validnom formatu(jpg, png)(100x100--300x300)"

            }
            this.selectedFile = null;
            this.previewUrl = null;
          }
        };
      };
      reader.readAsDataURL(file);
    }
  }

}