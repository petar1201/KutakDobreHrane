import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestTopBarComponent } from './guest-top-bar.component';

describe('GuestTopBarComponent', () => {
  let component: GuestTopBarComponent;
  let fixture: ComponentFixture<GuestTopBarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuestTopBarComponent]
    });
    fixture = TestBed.createComponent(GuestTopBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
