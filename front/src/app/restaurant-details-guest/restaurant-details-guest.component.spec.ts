import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantDetailsGuestComponent } from './restaurant-details-guest.component';

describe('RestaurantDetailsGuestComponent', () => {
  let component: RestaurantDetailsGuestComponent;
  let fixture: ComponentFixture<RestaurantDetailsGuestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RestaurantDetailsGuestComponent]
    });
    fixture = TestBed.createComponent(RestaurantDetailsGuestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
