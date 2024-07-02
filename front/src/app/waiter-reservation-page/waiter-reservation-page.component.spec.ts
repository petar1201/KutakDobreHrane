import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterReservationPageComponent } from './waiter-reservation-page.component';

describe('WaiterReservationPageComponent', () => {
  let component: WaiterReservationPageComponent;
  let fixture: ComponentFixture<WaiterReservationPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterReservationPageComponent]
    });
    fixture = TestBed.createComponent(WaiterReservationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
