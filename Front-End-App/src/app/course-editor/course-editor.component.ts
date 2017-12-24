import {Component, Input, OnInit} from '@angular/core';
import { CourseService } from "../course.service";
import {Course} from "../Models/course";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {School} from "../Models/school";
import {SchoolService} from "../school.service";
import {UserService} from "../user.service";
import {User} from "../Models/user";

@Component({
  selector: 'app-course-editor',
  templateUrl: './course-editor.component.html',
  styleUrls: ['./course-editor.component.css']
})
export class CourseEditorComponent implements OnInit {
  @Input() course: Course;
  errorMessage: string;
  schools: School[] = [];
  selectedSchoolId: number;

  constructor(
    private schoolService: SchoolService,
    private courseService: CourseService,
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.setOperation();
    this.getSchools();
  }

  setOperation(): void {
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

  getSchools(): void{
    this.schoolService.getSchoolIdList()
      .subscribe(
        data => {
          for (let id of data['school_ids']){
            this.schoolService.getSchool(id)
              .subscribe(
                school => {this.schools.push(school)}
              );
          }
        }
      );
  }

  save(): void {}

  // TODO: Upload PDF
  update(): void {
    this.course.school = this.selectedSchoolId;
    this.userService.getCurrentUser()
      .subscribe(
        user => { this.course.author =  user.id},
        err => { console.log(err) },
      () => {
          this.courseService.updateCourse(this.course)
            .subscribe(
              ()=>this.goBack(),
              err => { this.errorMessage = err },
              () => { this.errorMessage = null }
            );
      }
      );
  }

  insert(): void {
    this.course.school = this.selectedSchoolId;
    this.courseService.addCourse(this.course)
      .subscribe(
        () => {},
        err => {
          this.errorMessage = err;
        },
        () => { this.errorMessage = null }
      );
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
