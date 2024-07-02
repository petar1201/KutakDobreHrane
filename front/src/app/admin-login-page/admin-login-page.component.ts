import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminServiceService } from '../services/admin-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Admin } from '../types/admin';

@Component({
  selector: 'app-admin-login-page',
  templateUrl: './admin-login-page.component.html',
  styleUrls: ['./admin-login-page.component.css']
})
export class AdminLoginPageComponent implements OnInit{

  ngOnInit(): void {

  }


  loginPorukaGreske: String = "";

  username: String;
  password: String;

  constructor(private router: Router, private adminService: AdminServiceService, private route: ActivatedRoute){
    this.handleLoginError = this.handleLoginError.bind(this);

  }

  private handleLoginError(error: HttpErrorResponse){
    console.log("Login Failed")
    switch(error.status){
        case 306:
          this.loginPorukaGreske = "User not found"
          console.log("User not found")
          break;
        case 307:
          this.loginPorukaGreske = "User not active"
          console.log("User not active")
          break;
        case 308:
          console.log("Password not mathcing!")
          this.loginPorukaGreske = "Password not mathcing!"
          break;
        case 304:
          this.loginPorukaGreske = "Unknown error! Login Failed."
          console.log("Unknown error! Login Failed.")
          break;
        case 400:
          this.loginPorukaGreske = "Bad Params!"
          console.log("Bad params!")
          break;
        default:
          this.loginPorukaGreske = "Default error"
          console.log("Default error!")


    }
    return new Observable;
  }

  login(){
    console.log(this)
    this.loginPorukaGreske = ""
    this.adminService.login(this.username , this.password)
    .pipe(
      catchError(this.handleLoginError)
    )
    .subscribe(
      response_ =>{
        let admin : Admin = response_ as Admin
        console.log("login successful")
        console.log(admin)
        localStorage.setItem("admin", JSON.stringify(admin))
        this.router.navigate(["/admin"])
      }
    )
  }




  

}
