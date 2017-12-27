import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { School } from "../Models/school";
import { SchoolService } from "../school.service";
import { ActivatedRoute } from "@angular/router";
import { Course } from "../Models/course";
import {CourseService} from "../course.service";
import {UserService} from "../user.service";

@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrls: ['./school-detail.component.css']
})
export class SchoolDetailComponent implements OnInit {
  school: School;
  courses: Course[] = [];
  pageId: string;
  starred: boolean = false;
  starNum: number = 0;

  constructor(
    private route: ActivatedRoute,
    private schoolService: SchoolService,
    private courseService: CourseService,
    private userService: UserService,
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
          this.userService.getFavourites()
            .subscribe(
              data => {
                this.starred = data['school_ids'].indexOf(this.school.schoolid) > -1;
              }
            )
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

  favoriteSchool(){
    this.starred = !this.starred;
    this.school.star_num = this.starred? this.school.star_num + 1: this.school.star_num - 1;
    if (this.starred){
      this.userService.addFavouriteSchool(this.school.schoolid);
    }else{
      this.userService.unfavouriteSchool(this.school.schoolid);
    }
  }

  //TODO: get json from server
  getCourseTree(): void {

  }

  goBack(): void {
    this.location.back();
  }
}
