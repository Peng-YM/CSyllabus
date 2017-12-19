import { Component, OnInit } from '@angular/core';
import { School } from '../school';
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
    this.schoolService.getSchoolIdList()
      .subscribe(schools_id_list => this.schools_id_list = schools_id_list);
    for (const id of this.schools_id_list) {
      this.schoolService.getSchool(id)
        .subscribe(school => this.schools.push(school));
    }
  }

}
