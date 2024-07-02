import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterTopBarComponent } from './waiter-top-bar.component';

describe('WaiterTopBarComponent', () => {
  let component: WaiterTopBarComponent;
  let fixture: ComponentFixture<WaiterTopBarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterTopBarComponent]
    });
    fixture = TestBed.createComponent(WaiterTopBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
