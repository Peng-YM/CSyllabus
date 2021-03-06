import {Component, OnInit} from '@angular/core';
import {Course} from "../Models/course";
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../course.service";
import {Location} from "@angular/common";
import {User} from "../Models/user";
import {School} from "../Models/school";
import {SchoolService} from "../school.service";
import {MyConfiguration} from "../server.configuration";
import {UserService} from "../user.service";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  course: Course;
  author: User;
  school: School = new School();
  pdfSrc: string;
  pageId: string;
  starred: boolean = false;
  prerequisiteCourses: Course[];

  constructor(private route: ActivatedRoute,
              private courseService: CourseService,
              private schoolService: SchoolService,
              private userService: UserService,
              private location: Location) {
  }

  ngOnInit() {
    this.getCourseInformation();
  }

  getCourseInformation(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.pageId = `api/course/${id}`;
    this.pdfSrc = `${MyConfiguration.host}/api/course/${id}/syllabus`;
    this.courseService.getCourse(id)
      .subscribe(
        course => {
          this.course = course;
        },
        err => {
          console.log(err);
        },
        () => {
          this.getSchool();
          this.userService.getFavourites()
            .subscribe(
              data => {
                this.starred = data['course_ids'].indexOf(this.course.courseid) > -1;
              }
            );
        }
      );
  }

  favoriteCourse(): void {
    this.starred = !this.starred;
    this.course.star_num = this.starred ? this.course.star_num + 1 : this.course.star_num - 1;
    // add favourite
    if (this.starred){
      this.userService.addFavouriteCourse(this.course.courseid);
    }else {
      this.userService.unfavouriteCourse(this.course.courseid);
    }
  }

  getSchool(): void {
    this.schoolService.getSchool(this.course.school)
      .subscribe(
        school => {
          this.school = school;
        },
        err => {console.log(err);},
        () => {
          this.schoolService.getPrerequisiteCourses(this.school.schoolid, this.course.courseid)
            .subscribe(
              pre => {
                this.courseService.getMultipleCourses(pre)
                  .subscribe(
                    courses => {
                      this.prerequisiteCourses = courses.slice();
                    }
                  );
              }
            );
        }
      );
  }

  goBack(): void {
    this.location.back();
  }

}
