import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-restaurant-layout',
  templateUrl: './restaurant-layout.component.html',
  styleUrls: ['./restaurant-layout.component.css']
})
export class RestaurantLayoutComponent implements OnInit {
  @Input() layoutData: any;
  @Input() redArray: Array<Number> = []

  constructor() {}

  ngOnInit(): void {
    this.drawLayout();
  }

  drawLayout() {
    const canvas: any = document.getElementById('restaurantCanvas');
    const ctx = canvas.getContext('2d');

    // Clear the canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Set canvas size
    canvas.width = this.layoutData.width;
    canvas.height = this.layoutData.height;

    let counter = 0
    // Draw tables
    this.layoutData.tables.forEach((table: any) => {
      ctx.beginPath();
      ctx.arc(table.x, table.y, table.r, 0, 2 * Math.PI);

      if(this.redArray.includes(counter)){
        ctx.fillStyle = '#FF0000'
      }
      else{
        ctx.fillStyle = '#FFA500'; // Orange color for tables

      }
      ctx.fill();
      ctx.stroke();
      ctx.closePath();

      // Draw table capacity
      ctx.fillStyle = '#000';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(counter + ":" + table.capacity, table.x, table.y);
      counter++
    });

    // Draw kitchens
    this.layoutData.kitchens.forEach((kitchen: any) => {
      ctx.beginPath();
      ctx.rect(kitchen.x, kitchen.y, kitchen.width, kitchen.height);
      ctx.fillStyle = '#00FF00'; // Green color for kitchen
      ctx.fill();
      ctx.stroke();
      ctx.closePath();
    });

    // Draw toilets
    this.layoutData.toalets.forEach((toalet: any) => {
      ctx.beginPath();
      ctx.rect(toalet.x, toalet.y, toalet.width, toalet.height);
      ctx.fillStyle = '#0000FF'; // Blue color for toilets
      ctx.fill();
      ctx.stroke();
      ctx.closePath();
    });
  }
}
