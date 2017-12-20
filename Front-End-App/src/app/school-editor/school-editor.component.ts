import {Component, Input, OnInit} from '@angular/core';
import {SchoolService} from "../school.service";
import {School} from "../Models/school";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-school-editor',
  templateUrl: './school-editor.component.html',
  styleUrls: ['./school-editor.component.css']
})
export class SchoolEditorComponent implements OnInit {
  @Input() school: School;

  constructor(
    private schoolService: SchoolService,
    private location: Location,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
    if( id !== undefined ){
      this.schoolService.getSchool(id)
        .subscribe(school => this.school = school);
      this.save = this.update;
    }else {
      this.school = new School();
      this.save = this.insert;
    }

  }

  save(): void{}

  update(): void {
    this.schoolService.updateSchool(this.school)
      .subscribe(()=>this.goBack());
    console.log(`Updated School information: ${this.school}`);
  }

  insert(): void {
    this.schoolService.addSchool(this.school)
      .subscribe(()=>this.goBack());
    console.log(`Inserted School: ${this.school}`)
  }


  delete(): void {
    this.schoolService.deleteSchool(this.school.schoolid)
      .subscribe(()=>this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
