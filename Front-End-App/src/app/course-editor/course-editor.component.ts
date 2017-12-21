import {Component, Input, OnInit} from '@angular/core';
import { CourseService } from "../course.service";
import {Course} from "../Models/course";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-course-editor',
  templateUrl: './course-editor.component.html',
  styleUrls: ['./course-editor.component.css']
})
export class CourseEditorComponent implements OnInit {
  @Input() course: Course;
  errorMessage: string;
  constructor(
    private courseService: CourseService,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    if( id !== -1 ){
      this.courseService.getCourse(id)
        .subscribe(course => this.course = course);
      this.save = this.update;
    }else {
      this.course = new Course();
      this.save = this.insert;
    }
  }

  save(): void {}

  // TODO: Upload PDF
  update(): void {
    this.courseService.updateCourse(this.course)
      .subscribe(
        ()=>this.goBack(),
        err => { this.errorMessage = err },
        () => { this.errorMessage = null }
      );
    console.log(`Updated Course information: ${JSON.stringify(this.course)}`);
  }

  insert(): void {
    this.courseService.addCourse(this.course)
      .subscribe(
        () => {},
        err => {
          this.errorMessage = err;
        },
        () => { this.errorMessage = null }
      )
  }

  delete(): void {
    this.courseService.deleteCourse(this.course.courseid)
      .subscribe(
        () => {},
        err => {
          this.errorMessage = err;
        },
        () => { this.errorMessage = null }
      );
  }

  goBack(): void {
    this.location.back();
  }

}
