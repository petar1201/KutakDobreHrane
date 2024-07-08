import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrviBarComponent } from './prvi-bar.component';

describe('PrviBarComponent', () => {
  let component: PrviBarComponent;
  let fixture: ComponentFixture<PrviBarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrviBarComponent]
    });
    fixture = TestBed.createComponent(PrviBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
