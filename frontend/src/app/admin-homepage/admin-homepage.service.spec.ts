import { TestBed } from '@angular/core/testing';

import { AdminHomepageService } from './admin-homepage.service';

describe('AdminHomepageService', () => {
  let service: AdminHomepageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminHomepageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
