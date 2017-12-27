import {Component, OnInit} from '@angular/core';
import {School} from '../Models/school';
import {SchoolService} from '../school.service';

@Component({
  selector: 'app-school-dashboard',
  templateUrl: './school-dashboard.component.html',
  styleUrls: ['./school-dashboard.component.css']
})
export class SchoolDashboardComponent implements OnInit {
  schools: School[] = [];

  constructor(private schoolService: SchoolService) {
  }

  ngOnInit() {
    this.getSchools();
  }

  getSchools(): void {
    this.schoolService.getSchoolIdList()
      .subscribe(
        data => {
          this.schoolService.getMultiSchools(data['school_ids'])
            .subscribe(
              schools => {
                // sort by star num
                this.schools = schools.sort((a, b) => a.star_num - b.star_num);
                console.log(this.schools[0]);
              }
            )
        },
        err => {
          console.log(err);
        },
        () => {

        });
  }

}
