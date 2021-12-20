import { TestBed } from '@angular/core/testing';

import { UserinfoService } from './userinfo.service';

describe('UserinfoService', () => {
  let service: UserinfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserinfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
