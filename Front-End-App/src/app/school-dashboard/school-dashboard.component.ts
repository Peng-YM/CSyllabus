import { Component, OnInit } from '@angular/core';
import { School } from '../Models/school';
import { SchoolService } from '../school.service';

@Component({
  selector: 'app-school-dashboard',
  templateUrl: './school-dashboard.component.html',
  styleUrls: ['./school-dashboard.component.css']
})
export class SchoolDashboardComponent implements OnInit {
  schools: School[] = [];
  schools_id_list: number[] = [];
  constructor(
    private schoolService: SchoolService
  ) { }

  ngOnInit() {
    this.getSchools();
  }

  getSchools(): void {
    // this.schoolService.getSchoolIdList().subscribe(
    //   data => {
    //     this.schools_id_list = data['schoolid'];
    //   },
    //   err => {
    //     console.log(err);
    //   }
    // );
    this.schools_id_list.push(4);

    for (const id of this.schools_id_list) {
      this.schoolService.getSchool(id).subscribe(
        data => {this.schools.push(data)},
        err => {
          console.log(err);
        }
      );
    }
  }

}
