import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestProfilePageComponent } from './guest-profile-page.component';

describe('GuestProfilePageComponent', () => {
  let component: GuestProfilePageComponent;
  let fixture: ComponentFixture<GuestProfilePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuestProfilePageComponent]
    });
    fixture = TestBed.createComponent(GuestProfilePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
