import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantDetailsAdminComponent } from './restaurant-details-admin.component';

describe('RestaurantDetailsAdminComponent', () => {
  let component: RestaurantDetailsAdminComponent;
  let fixture: ComponentFixture<RestaurantDetailsAdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RestaurantDetailsAdminComponent]
    });
    fixture = TestBed.createComponent(RestaurantDetailsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
