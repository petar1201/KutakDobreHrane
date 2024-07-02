import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-delivery-time-dialog',
  templateUrl: './delivery-time-dialog.component.html',
  styleUrls: ['./delivery-time-dialog.component.css']
})
export class DeliveryTimeDialogComponent {
  selectedTime: string;

  constructor(
    public dialogRef: MatDialogRef<DeliveryTimeDialogComponent>
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}