import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterStatisticsPageComponent } from './waiter-statistics-page.component';

describe('WaiterStatisticsPageComponent', () => {
  let component: WaiterStatisticsPageComponent;
  let fixture: ComponentFixture<WaiterStatisticsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WaiterStatisticsPageComponent]
    });
    fixture = TestBed.createComponent(WaiterStatisticsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
