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
  schools_id_list: number[] = [];

  constructor(private schoolService: SchoolService) {
  }

  ngOnInit() {
    this.getSchools();
  }

  getSchools(): void {
    this.schoolService.getSchoolIdList()
      .subscribe(
        data => {
          for (let id of data['school_ids']) {
            this.schools_id_list.push(id);
            this.schoolService.getSchool(id).subscribe(
              data => {
                this.schools.push(data)
              },
              err => {
                console.log(err);
              }
            );
          }
        },
        err => {
          console.log(err);
        },
        () => {
          this.schools_id_list.sort((a, b) => a - b);
          this.schools.sort((a, b) => {
            if(a.school_name > b.school_name){
              return 1;
            }
            if (a.school_name >= b.school_name) {
              return 0;
            } else {
              return -1;
            }
          });
          console.log("Load schools successfully");
        });
  }

}
