import { TestBed } from '@angular/core/testing';

import { ApplyService } from './apply.service';

describe('ApplyService', () => {
  let service: ApplyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApplyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
