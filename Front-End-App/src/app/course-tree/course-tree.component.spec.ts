import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseTreeComponent } from './course-tree.component';

describe('CourseTreeComponent', () => {
  let component: CourseTreeComponent;
  let fixture: ComponentFixture<CourseTreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CourseTreeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
