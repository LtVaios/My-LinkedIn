import { TestBed } from '@angular/core/testing';

import { MywebService } from './myweb.service';

describe('MywebService', () => {
  let service: MywebService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MywebService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
