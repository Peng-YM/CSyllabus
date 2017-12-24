import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { School } from "../Models/school";
import { SchoolService } from "../school.service";
import { ActivatedRoute } from "@angular/router";
import { Course } from "../Models/course";
import {CourseService} from "../course.service";

@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrls: ['./school-detail.component.css']
})
export class SchoolDetailComponent implements OnInit {
  school: School;
  courses: Course[] = [];
  constructor(
    private route: ActivatedRoute,
    private schoolService: SchoolService,
    private courseService: CourseService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getSchoolInformation();
  }

  getSchoolInformation(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.schoolService.getSchool(id)
      .subscribe(
        school => { this.school = school; },
        err => {console.log(err)},
        () => {
          this.getCourses();
        }
      );
  }

  getCourses(): void {
    this.schoolService.getCourseIdList(this.school.schoolid)
      .subscribe(
        data => {
          for (let course_id of data['course_ids']) {
            this.courseService.getCourse(course_id)
              .subscribe(
                course => { this.courses.push(course) }
              )
          }
        },
        err => {
          console.log(err);
        }
      );
  }

  //TODO: get json from server
  getCourseTree(): void {

  }

  goBack(): void {
    this.location.back();
  }
}
