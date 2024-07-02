import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { User } from '../types/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { AdminServiceService } from '../services/admin-service.service';

@Component({
  selector: 'app-waiter-profile-page',
  templateUrl: './waiter-profile-page.component.html',
  styleUrls: ['./waiter-profile-page.component.css']
})
export class WaiterProfilePageComponent implements OnInit{

  user: User;
  editMode: boolean = false;
  editedUser: User; // Optional: Use this to track changes locally before saving
  newPassword: string = ''; // Track new password separately
  errorMesage: string = "";
  
  constructor(
    private router: Router, 
    private userService: UserServiceService, 
    private adminService: AdminServiceService, 
    private route: ActivatedRoute

  ){
    this.handleUpdateError = this.handleUpdateError.bind(this);

  }

  private handleUpdateError(error: HttpErrorResponse){
    console.log("Update Failed")
    this.errorMesage = "Password invalid Format!"
    return new Observable;
  }

  private handleRestaurantError(error: HttpErrorResponse){
    console.log("Find Res failed")
    this.errorMesage = "User not found"
    return new Observable;
  }



  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem("logged"));
    this.userService.findRestaurant(this.user.id).pipe(
      catchError(this.handleRestaurantError)
    ).subscribe(
      response =>{
        this.user.idRes = response as Number
      }
    )
    // Optional: Make a copy of user object to track changes locally before saving
    // this.editedUser = { ...this.user };
  }

  toggleEditMode(): void {
    this.editMode = true;
    // Optional: Initialize editedUser with a copy of user object
    // this.editedUser = { ...this.user };
  }

  saveChanges(): void {
    console.log(this.user);
      // Update user data in backend using userService or adminService
      // Example: this.userService.updateUser(this.editedUser).subscribe(...);
      // Optional: Update local user object with editedUser
      // this.user = { ...this.editedUser };
      if (this.newPassword.trim() !== '') {
        this.user.password = this.newPassword; // Update user password
        // Example: this.userService.updateUser(this.user).subscribe(...);
      }
    this.userService.updateUser(this.user)
    .pipe(
      catchError(this.handleUpdateError)
    ).subscribe(
      response => {
        localStorage.setItem("logged", JSON.stringify(response))
      }
    )
    this.editMode = false;
  }

  cancelEdit(): void {
    this.editMode = false;
    // Optional: Reset editedUser to original user object
    // this.editedUser = { ...this.user };
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
            this.user.profilePhoto = this.src
            this.selectedFile = file;
            this.previewUrl = e.target.result;
            this.errorMesage = ""
          } else {
            if(!isSizeValid){
              this.errorMesage = "Slika nije u validnom formatu(100x100--300x300)"
            }
            else if(!isFormatValid){
              this.errorMesage = "Slika nije u validnom formatu(jpg, png)"
            }
            if(!isSizeValid && !isFormatValid){
              this.errorMesage = "Slika nije u validnom formatu(jpg, png)(100x100--300x300)"

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
