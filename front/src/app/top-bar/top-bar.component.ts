import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../types/user';
import UserType from '../types/userType';
import { Observable, catchError } from 'rxjs';


@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit{

  constructor(private router: Router, private userService: UserServiceService, private route: ActivatedRoute){
    this.handleLoginError = this.handleLoginError.bind(this);
    this.handleRegisterError = this.handleRegisterError.bind(this)
    this.handleLoginResetError = this.handleLoginResetError.bind(this)
    this.setEmptryErrosMessages = this.setEmptryErrosMessages.bind(this)
  }

  ngOnInit(): void {
    this.loginPorukaGreske = ""
    this.registerPorukaGreske = ""
    this.resetPassPorukaGreske = ""
  }

  Object = Object;

  usernameL: String;
  passwordL: String;
  username: String;
  password: String;
  usernameReset: String;
  passwordReset1: String;
  passwordReset2: String;
  oldPassword: String;
  registerFlag: Boolean = false;
  resetPasswordFlag: Boolean = false;
  securityQuestion: String;
  securityAnswer: String;
  firstName: String;
  lastName: String;
  gender: String = "";
  address: String;
  phoneNumber: String;
  email: String;
  type: UserType = UserType.GUEST;
  school: String = "";
  loginPorukaGreske: String = "";
  registerPorukaGreske: String = "";
  resetPassPorukaGreske: String = "";

  profilePhoto: String = "";
  cardNumber: String = "";

  private handleLoginResetError(error: HttpErrorResponse){
    console.log("Login Failed")
    this.resetPasswordFlag = false;
    switch(error.status){
        case 306:
          this.resetPassPorukaGreske = "User not found"
          console.log("User not found")
          break;
        case 307:
          this.resetPassPorukaGreske = "User not active"
          console.log("User not active")
          break;
        case 308:
          console.log("Password not mathcing!")
          this.resetPassPorukaGreske = "Old password is not correct!"
          this.resetPasswordFlag = true;
          break;
        case 304:
          this.resetPassPorukaGreske = "Unknown error! Login Failed."
          console.log("Unknown error! Authentication of user Failed.")
          break;
        case 400:
          this.resetPassPorukaGreske = "Bad Params!"
          console.log("Bad params!")
          break;
        default:
          this.loginPorukaGreske = "Default error"
          console.log("Default error!")


    }
    return new Observable;
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
    this.registerFlag = false
    this.resetPasswordFlag = false
    this.loginPorukaGreske = ""
    this.userService.login(this.usernameL , this.passwordL)
    .pipe(
      catchError(this.handleLoginError)
    )
    .subscribe(
      response_ =>{
        let user : User = response_ as User
        console.log("login successful")
        console.log(user)
      }
    )
  }

  
  checkParams(): Boolean{
    if(
      this.username == null ||
      this.password == null ||
      this.securityQuestion == null ||
      this.securityAnswer == null ||
      this.firstName == null ||
      this.lastName == null ||
      this.gender == null ||
      this.address == null ||
      this.phoneNumber == null ||
      this.email == null ||
      this.type == null    
    ){
      return false
    }
    else{
      return true
    }
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
    stud.profilePhoto = this.profilePhoto || "null";
    stud.cardNumber = this.cardNumber || ""; 
    stud.type = this.type;

    this.userService.registerUser(stud).pipe(
        catchError(this.handleRegisterError)
    )
    .subscribe(
        response_ => {
          this.registerPorukaGreske = ""
          console.log(stud)
      }
    )
    }
  

  resetPassword(){
    //TODO CALL RESET FROM SERVICE NOT LOGIN!!!!!!
    this.userService.login(this.usernameReset, this.oldPassword).pipe(
      catchError(this.handleLoginResetError)
    ).subscribe(
      response => {

        this.resetPasswordFlag = false;
      }
    )
  }

  private setEmptryErrosMessages(){
    this.registerPorukaGreske  = ""
    this.loginPorukaGreske = ""
    this.resetPassPorukaGreske = ""
  }

  registerClick(){
    console.log("REGG")
    this.setEmptryErrosMessages()
    this.registerFlag = true
    this.resetPasswordFlag = false
  }

  resetPasswordClick(){
    this.setEmptryErrosMessages()
    this.resetPasswordFlag = true
    this.registerFlag = false
  }


}