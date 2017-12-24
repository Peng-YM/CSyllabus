import {Component, Input, OnInit, ViewChild} from '@angular/core';
import { CourseService } from "../course.service";
import {Course} from "../Models/course";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {School} from "../Models/school";
import {SchoolService} from "../school.service";
import {UserService} from "../user.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MyConfiguration} from "../server.configuration";


@Component({
  selector: 'app-course-editor',
  templateUrl: './course-editor.component.html',
  styleUrls: ['./course-editor.component.css']
})
export class CourseEditorComponent implements OnInit {
  @Input() course: Course;
  @ViewChild('fileInput') fileInput;

  errorMessage: string;
  schools: School[] = [];
  selectedSchoolId: number;
  coursesInSchool: Course[] = [];

  constructor(
    private schoolService: SchoolService,
    private courseService: CourseService,
    private userService: UserService,
    private location: Location,
    private route: ActivatedRoute,
    private http: HttpClient
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
                school => {this.schools.push(school)},
                err => {
                  console.log(err);
                }
              );
          }
        }
      );
  }

  getPrerequisite(): void{
    this.coursesInSchool = [];
    if(this.selectedSchoolId !== undefined){
      this.schoolService.getCourseIdList(this.selectedSchoolId)
        .subscribe(
          data => {
            for (let id of data['course_ids']){
              if (id !== this.course.course_id){
                this.courseService.getCourse(id)
                  .subscribe(
                    course => {
                      this.coursesInSchool.push(course);
                    }
                  );
              }
            }
          }
        );
    }
  }

  save(): void {}

  insert(): void {
    this.course.school = +this.selectedSchoolId;
    this.userService.getCurrentUser()
      .subscribe(
        user => { this.course.author =  user.id},
        err => { console.log(err) },
      () => {
          this.courseService.addCourse(this.course)
            .subscribe(
              course => {
                this.uploadPdf(this.course.course_id);
              },
              err => { this.errorMessage = err },
              () => {
                this.errorMessage = null;
                this.goBack();
              }
            );
      }
      );
  }

  update(): void {
    this.course.school = +this.selectedSchoolId;
    console.log(this.course);
    this.courseService.updateCourse(this.course)
      .subscribe(
        course => {
          this.uploadPdf(this.course.course_id);
        },
        err => {
          this.errorMessage = err;
        },
        () => { this.errorMessage = null }
      );
  }

  uploadPdf(id: number): void {
    const URL = `${MyConfiguration.host}/api/course/${id}/syllabus`;
    let httpOptions =  {
      headers: new HttpHeaders(
        {
          'Content-Type': 'multipart/form-data',
          'Charset': 'utf-8'
        })
    };
    let fileBrowser = this.fileInput.nativeElement;
    if(fileBrowser.files && fileBrowser.files[0]){
      const formData = new FormData();
      formData.append("file", fileBrowser.files[0]);
      this.http.post(URL, formData, httpOptions)
        .subscribe(
          () => {},
          err => {
            console.log(err);
          }
        );
    }
  }

  delete(): void {
    this.courseService.deleteCourse(this.course.course_id)
      .subscribe(
        () => {},
        err => {
          this.errorMessage = err;
        },
        () => {
          this.errorMessage = null;
          this.goBack();
        }
      );
  }

  goBack(): void {
    this.location.back();
  }
}
