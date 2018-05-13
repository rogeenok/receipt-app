import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiptListMapComponent } from './receipt-list-map.component';

describe('ReceiptListMapComponent', () => {
  let component: ReceiptListMapComponent;
  let fixture: ComponentFixture<ReceiptListMapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceiptListMapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiptListMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
