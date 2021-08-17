import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobIdComponent } from './job-id.component';

describe('JobIdComponent', () => {
  let component: JobIdComponent;
  let fixture: ComponentFixture<JobIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobIdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
