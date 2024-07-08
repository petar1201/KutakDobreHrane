import { Component, OnInit } from '@angular/core';
import { ReservationDto } from '../types/reservationDto';
import { ReservationService } from '../services/reservation.service';
import { RestaurantService } from '../services/restaurant.service';
import { Router } from '@angular/router';
import { User } from '../types/user';

@Component({
  selector: 'app-reservation-details',
  templateUrl: './reservation-details.component.html',
  styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit{

  constructor(
    private reservationService: ReservationService,
    private restaurantService: RestaurantService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.reservation = JSON.parse(localStorage.getItem("reservation")) as ReservationDto
    this.reservationService.getTables(this.reservation.id).subscribe(
      res =>{
        this.tableMap = res as Map<Number, Number>;
        console.log(this.tableMap)
      }
    )

  }

  reservation: ReservationDto;
  tableMap: Map<Number, Number>;
  description: String;


  formatDate(dateString: string): string {
    console.log(dateString)
    const date = new Date(dateString);
    date.setSeconds(0);
    date.setMilliseconds(0);
    date.setHours(date.getHours()+2)
    return date.toISOString().slice(0, 16).replace('T', ' ');
  }

  goBack(): void {
    this.router.navigate(["waiter/reservations"]);
  }
  
  gett(){
    return JSON.parse(this.reservation.restaurantLayout as string)
  }

  isCreated(){
    if(this.reservation.status==="CREATED") return true
    return false
  }

  isThereAnyAvailable(){
    let i = 0
    while(true){
      if(this.tableMap[i]===undefined){
        return false
      }
      if(this.tableMap[i]!==-1 && this.tableMap[i]>=this.reservation.numOfPeople){
        return true
      }
      i++
    }

  }

  selectedTable : Number;

  getWaiterId(){
    return((JSON.parse(localStorage.getItem("logged")) as User).id)
  }

  getOnlyTable(){
    if(this.getObjectArrayOfAvailableTables().length === 0){
      return -1
    }
    else return this.getObjectArrayOfAvailableTables()[0]["num"]
  }

  accept(){

    if(this.selectedTable === undefined){
      this.selectedTable = this.getOnlyTable()
    }


    console.log((this.selectedTable as number)-1)
    this.reservationService.accept(
      this.reservation.id, this.getWaiterId(), (this.selectedTable as number)-1
    ).subscribe(
      res =>{
        localStorage.setItem("reservation", JSON.stringify(res as ReservationDto))
        this.ngOnInit()
        location.reload()
      }
    )
  }

  getIndexArrayOfColoredTables(){
    const array = []


   let i = 0

   while(true){
    if(this.tableMap[i]===undefined){
      break
    }
    if(this.tableMap[i]===-1 || this.tableMap[i] < this.reservation.numOfPeople){
      array.push(i)
    }

    i++
   }

    return array
  }

  getObjectArrayOfAvailableTables(){
    const array = []


    let i = 0
 
    while(true){
     if(this.tableMap[i]===undefined){
       break
     }
     if(this.tableMap[i]!==-1 && this.tableMap[i] >= this.reservation.numOfPeople){
       const data = {
         "num":i+1,
         "cap":this.tableMap[i]
       }
       array.push(data)
     }
 
     i++
    }
 
 
     return array
  }

  refuse(){
    if(this.description === undefined){
      alert("You must enter reason when declining")
      return
    }
    this.reservationService.refuse(this.reservation.id, this.getWaiterId(), this.description).subscribe(
      res =>{
        localStorage.setItem("reservation", JSON.stringify(res as ReservationDto))
        this.router.navigate(["waiter/reservations"])
      }
    )
  }


  show(){
    return true;
  }

}
