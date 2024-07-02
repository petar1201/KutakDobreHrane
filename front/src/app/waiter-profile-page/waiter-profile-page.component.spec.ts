import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterProfilePageComponent } from './waiter-profile-page.component';

describe('WaiterProfilePageComponent', () => {
  let component: WaiterProfilePageComponent;
  let fixture: ComponentFixture<WaiterProfilePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterProfilePageComponent]
    });
    fixture = TestBed.createComponent(WaiterProfilePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
