import { Component, OnInit } from '@angular/core';
import { ChartType } from 'chart.js';
import { ReservationService } from '../services/reservation.service';
import { Histogram } from '../types/histogram';
import { User } from '../types/user';

@Component({
  selector: 'app-prvi-bar',
  templateUrl: './prvi-bar.component.html',
  styleUrls: ['./prvi-bar.component.css']
})
export class PrviBarComponent implements OnInit{
  barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  barChartLabels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
  barChartType: ChartType= 'bar';
  barChartLegend = true;

  barChartData = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' }
  ];

  constructor(
    private reservationService: ReservationService
  ){

  }

  ngOnInit(): void {
    this.reservationService.bar((JSON.parse(localStorage.getItem("logged")) as User).id).subscribe(
      res =>{
        let dataa = []
        let hist = res as Histogram
        dataa.push(hist.mon)
        dataa.push(hist.tue)
        dataa.push(hist.wed)
        dataa.push(hist.thu)
        dataa.push(hist.fri)
        dataa.push(hist.sat)
        dataa.push(hist.sun)

        this.barChartData = [{data:dataa, label:"Count"}]
      }
    )
}

}