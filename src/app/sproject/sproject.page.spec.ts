import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SProjectPage } from './sproject.page';

describe('SProjectPage', () => {
  let component: SProjectPage;
  let fixture: ComponentFixture<SProjectPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SProjectPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SProjectPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
