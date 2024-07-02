import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../types/user';
import UserType from '../types/userType';
import { Observable, catchError } from 'rxjs';
import { NgForm } from '@angular/forms';


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
  usernameReset: string;
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

  cardNumber: String = "";

  private handleLoginResetError(error: HttpErrorResponse){
    console.log("Login Failed")
    this.resetPasswordFlag = false;

    localStorage.setItem("reg", null)
    localStorage.setItem("res", null)
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
    localStorage.setItem("reg", null)
    localStorage.setItem("res", null)

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
        localStorage.setItem("logged", JSON.stringify(user))
        if(user.type===UserType.WAITER){
          this.router.navigate(["/waiter/profile"])

        }
        else{
          this.router.navigate(["/guest/profile"])
        }
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
    if(this.src !== ""){
      stud.profilePhoto = this.src
    }
    else{
      stud.profilePhoto = "data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsBAMAAACLU5NGAAAAG1BMVEUAmf8fpf8/sv9fv/9/zP+f2P+/5f/f8v/////4L7EgAAACv0lEQVR42u3XsW/aQBiG8fcMJBmd0gRGQxWVkSZqxIiyNCPN0DJStUo9hqptPAaIzf3ZHYzxGYdMV6bnt/BIkaLT58uHIwEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPhnrq7CTb67rFdd++rDpoKrXq08ubDWrseSZKbWZmG16oKptfa3JOnMWvttpzxpZTehuc4kafDcM59W1aqb/uipHc8kBfYmfJsMK+VLM5Kk24lk1qFk4sitF9xIUjOV1J1LOl9Wyq+jJ+loIUmdB7f2iiXFoSTzWCm/mgtpMJGk5sqtvW5DBWleQ6c8ayykaX7HM7f2GoVqLSRJJzOnPGs9SY/bx1OWJDXzQzYqV20qHT8Uz78sz7oTKd0+nrJU3Gdp5M7CpNLJpHj+ZXneqI+hgufN4xmWlS+qdSipUXmknbk0GOdTXDnlVTD9Vv7SwbCszed8d1gXWVieeuWUR19+2jtJjc3a6Y7L0nZczrBa90kaSRrll808O+XzWPfJn+i1Y2kwd4fVuv+5vvv/x5LMdfrqsYJ1u7os2vHkAMeSRpPXjqVBsrOSGulBjtVcbi/sYBjsXHmpaXdfJ26j4sfByinfGyItF0RU1naY8by+6Iq1sHTKt2z/OpUaWb67HCezA6xT6e/+Lx9pNMt3l+N4dogvH6XFcEylNsMqVr0zrUkxnK5bvt8gVq+82Ixmxap3/nSHh3ixOV5IR0tJ6s7c0vbbcGdcSSjFkSSTyS1fzkJJJh5K5jGSTBK55RxvkH+Yz5J0vpLU+SWps6yUL53s8rQ9fZak92nvzXRZLUmNzarPp2WS773Tj3YsKUjuTi9sVClvrq21aX7Hv9brhWsYW2s/55NO6uXvtaa//c/zTb9edf1+cWLTj2oFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADg3z9bSadHng4eqAAAAABJRU5ErkJggg=="
    }
    stud.cardNumber = this.cardNumber || ""; 
    stud.type = this.type;

    this.userService.registerUser(stud).pipe(
        catchError(this.handleRegisterError)
    )
    .subscribe(
        response_ => {





              this.setEmptryErrosMessages()
              this.registerFlag = false
              localStorage.setItem("reg", null)
              localStorage.setItem("res", null)




          console.log(stud)
      }
    )
    }
  

  resetPassword(){
    if(this.passwordReset1 != this.passwordReset2){
      this.resetPassPorukaGreske = "Password not matching!"
      return
    }
    this.userService.resetPassword(this.usernameReset, this.oldPassword, this.passwordReset1).pipe(
      catchError(this.handleLoginResetError)
    ).subscribe(
      response => {
        this.resetPasswordFlag = false;

        localStorage.setItem("reg", null)
        localStorage.setItem("res", null)
        this.resetPassPorukaGreske = ""
      }
    )
  }

  private setEmptryErrosMessages(){
    this.registerPorukaGreske  = ""
    this.loginPorukaGreske = ""
    this.resetPassPorukaGreske = ""
  }

  registerClick(){
    if(!this.registerFlag){
      localStorage.setItem("reg", "1")
    }
    else{
      localStorage.setItem("reg", null)

    }
    console.log("REGG")
    this.setEmptryErrosMessages()
    this.registerFlag = !this.registerFlag
    this.resetPasswordFlag = false

    localStorage.setItem("reg", null)
    localStorage.setItem("res", null)
  }

  resetPasswordClick(){
    if(!this.resetPasswordFlag){
      localStorage.setItem("res", "1")
    }   
    else{
      localStorage.setItem("res", null)

    }
    this.setEmptryErrosMessages()
    this.resetPasswordFlag = !this.resetPasswordFlag
    this.registerFlag = false
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