import { Component, OnInit } from '@angular/core';
import { ChartData, ChartDataset, ChartOptions, ChartType, ChartTypeRegistry } from 'chart.js';
import { ReservationService } from '../services/reservation.service';
import { User } from '../types/user';
import { Pie } from '../types/pie';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit{

  // Pie
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };
  public pieChartLabels = [];
  public pieChartDatasets = [ {
    data: [ 3, 2, 10, 5 ]
  } ];
  public pieChartLegend = true;
  public pieChartPlugins = [];

  constructor(
    private reservationService: ReservationService
  ) {
  }

  lista: Array<Pie>;

  ngOnInit(): void {
      console.log(JSON.parse(localStorage.getItem("logged")) as User)
      this.reservationService.pie((JSON.parse(localStorage.getItem("logged")) as User).id).subscribe(
        res =>{
          let dataa = []
          this.lista = res as  Array<Pie>;
          this.lista.forEach(
            lis =>{
              dataa.push(lis.num)
              this.pieChartLabels.push(lis.name)
            }
          )
          this.pieChartDatasets = [{data:dataa}]
        }
      )
  }

} 