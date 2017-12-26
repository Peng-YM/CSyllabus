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
import {CourseTreeService} from "../course-tree.service";


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
  coursesInSchool: Course[];
  prerequisites: {[id: number]: boolean};
  selectedFiles: FileList;

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
          this.schoolService.getMultiSchools(data['school_ids'])
            .subscribe(
              schools => {
                this.schools = schools.slice();
              }
            )
        }
      );
  }

  getPrerequisite(): void{
    this.coursesInSchool = [];
    this.prerequisites = {};
    if(this.selectedSchoolId !== undefined){
      this.schoolService.getCourseIdList(this.selectedSchoolId)
        .subscribe(
          data => {
            this.courseService.getMultipleCourses(data['course_ids'])
              .subscribe(
                courses => {
                  for (let c of courses){
                    if(c.courseid !== this.course.courseid){
                      this.coursesInSchool.push(c);
                      this.prerequisites[c.courseid] = false;
                    }
                  }
                }
              );
          });
      if(this.course.courseid !== undefined){
        this.schoolService.getCourseTree(this.selectedSchoolId)
          .subscribe(
            tree => {
              const ancestors = CourseTreeService.findAncestors(tree, this.course.courseid);
              for (let courseid of ancestors){
                this.prerequisites[courseid] = true;
              }
            }
          );
      }
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
                this.uploadPdf(this.course.courseid);
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
    this.courseService.updateCourse(this.course)
      .subscribe(
        course => {
          this.uploadPdf(this.course.courseid);
        },
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
        () => {
          this.errorMessage = null;
          this.goBack();
        }
      );
  }

  uploadPdf(id: number): void {
    const URL = `${MyConfiguration.host}/api/course/${id}/syllabus`;
    let syllabusFile: File = this.selectedFiles.item(0);
    console.log(syllabusFile);
    const formData = new FormData();
    formData.append("file", syllabusFile);
    this.http.post(URL, formData)
      .subscribe();
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }



  goBack(): void {
    this.location.back();
  }
}
