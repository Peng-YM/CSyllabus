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
  pageId: string;

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
    this.pageId = `/school/${id}`;
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
          this.courseService.getMultipleCourses(data['course_ids'])
            .subscribe(
              data => {
                this.courses = data.slice();
              }
            );
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
