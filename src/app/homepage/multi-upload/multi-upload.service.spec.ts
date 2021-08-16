import { TestBed } from '@angular/core/testing';

import { MultiUploadService } from './multi-upload.service';

describe('MultiUploadService', () => {
  let service: MultiUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MultiUploadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
