import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCheckComponent } from './show-check.component';

describe('ShowCheckComponent', () => {
  let component: ShowCheckComponent;
  let fixture: ComponentFixture<ShowCheckComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowCheckComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
