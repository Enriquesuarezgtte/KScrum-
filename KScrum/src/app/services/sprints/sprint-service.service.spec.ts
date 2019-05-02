import { TestBed } from '@angular/core/testing';

import { SprintServiceService } from './sprint-service.service';

describe('SprintServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SprintServiceService = TestBed.get(SprintServiceService);
    expect(service).toBeTruthy();
  });
});
