import { TestBed, inject } from '@angular/core/testing';

import { CourseTreeService } from './course-tree.service';

describe('CourseTreeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CourseTreeService]
    });
  });

  it('should be created', inject([CourseTreeService], (service: CourseTreeService) => {
    expect(service).toBeTruthy();
  }));
});
