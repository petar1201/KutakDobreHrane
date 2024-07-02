import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryTimeDialogComponent } from './delivery-time-dialog.component';

describe('DeliveryTimeDialogComponent', () => {
  let component: DeliveryTimeDialogComponent;
  let fixture: ComponentFixture<DeliveryTimeDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeliveryTimeDialogComponent]
    });
    fixture = TestBed.createComponent(DeliveryTimeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
