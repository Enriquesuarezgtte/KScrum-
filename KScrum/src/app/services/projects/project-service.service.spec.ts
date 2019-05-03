import { TestBed } from '@angular/core/testing';
import { ProjectServiceService } from './project-service.service';
import { AngularFirestore } from "@angular/fire/firestore";
describe('ProjectServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProjectServiceService = TestBed.get(ProjectServiceService);
    expect(service).toBeTruthy();
  });
});
