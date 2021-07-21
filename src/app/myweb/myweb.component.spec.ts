import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MywebComponent } from './myweb.component';

describe('MywebComponent', () => {
  let component: MywebComponent;
  let fixture: ComponentFixture<MywebComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MywebComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MywebComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
