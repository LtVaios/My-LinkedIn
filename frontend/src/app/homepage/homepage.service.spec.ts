import { TestBed } from '@angular/core/testing';

import { HomepageService } from './homepage.service';

describe('HomepageService', () => {
  let service: HomepageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomepageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
