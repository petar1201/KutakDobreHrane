import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestRestaurantsPageComponent } from './guest-restaurants-page.component';

describe('GuestRestaurantsPageComponent', () => {
  let component: GuestRestaurantsPageComponent;
  let fixture: ComponentFixture<GuestRestaurantsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuestRestaurantsPageComponent]
    });
    fixture = TestBed.createComponent(GuestRestaurantsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
