import { TestBed } from '@angular/core/testing';

import { NotaServiceService } from './nota-service.service';

describe('NotaServiceService', () => {
  let service: NotaServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotaServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
