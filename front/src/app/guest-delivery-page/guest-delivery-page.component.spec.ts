import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestDeliveryPageComponent } from './guest-delivery-page.component';

describe('GuestDeliveryPageComponent', () => {
  let component: GuestDeliveryPageComponent;
  let fixture: ComponentFixture<GuestDeliveryPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuestDeliveryPageComponent]
    });
    fixture = TestBed.createComponent(GuestDeliveryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
