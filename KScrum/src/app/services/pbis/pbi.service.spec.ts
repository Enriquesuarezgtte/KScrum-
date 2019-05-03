import { TestBed } from '@angular/core/testing';

import { PbiService } from './pbi.service';

describe('PbiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PbiService = TestBed.get(PbiService);
    expect(service).toBeTruthy();
  });
});
