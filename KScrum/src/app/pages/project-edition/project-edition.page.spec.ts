import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectEditionPage } from './project-edition.page';

describe('ProjectEditionPage', () => {
  let component: ProjectEditionPage;
  let fixture: ComponentFixture<ProjectEditionPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectEditionPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectEditionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
