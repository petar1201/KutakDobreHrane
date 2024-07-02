import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterDeliveryPageComponent } from './waiter-delivery-page.component';

describe('WaiterDeliveryPageComponent', () => {
  let component: WaiterDeliveryPageComponent;
  let fixture: ComponentFixture<WaiterDeliveryPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterDeliveryPageComponent]
    });
    fixture = TestBed.createComponent(WaiterDeliveryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
