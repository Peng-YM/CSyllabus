import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { School } from "../Models/school";
import { SchoolService } from "../school.service";
import { ActivatedRoute } from "@angular/router";
import { Course } from "../Models/course";

@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrls: ['./school-detail.component.css']
})
export class SchoolDetailComponent implements OnInit {
  school: School;
  courses: Course;
  constructor(
    private route: ActivatedRoute,
    private schoolService: SchoolService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getSchoolInformation();
  }

  getSchoolInformation(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.schoolService.getSchool(id)
      .subscribe(school => this.school = school);
  }

  getCourses(): void {

  }

  goBack(): void {
    this.location.back();
  }
}
