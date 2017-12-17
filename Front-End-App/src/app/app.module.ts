import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { SchoolDashboardComponent } from './school-dashboard/school-dashboard.component';
import { SchoolDetailComponent } from './school-detail/school-detail.component';
import { CourseDetailComponent } from './course-detail/course-detail.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { SchoolService } from './school.service';
import { UserService } from './user.service';
import { CourseService } from './course.service';
import { SchoolRoutingModule } from './school-routing/school-routing.module';
import { CourseRoutingModule } from './course-routing/course-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    SchoolDashboardComponent,
    SchoolDetailComponent,
    CourseDetailComponent,
    UserDetailComponent
  ],
  imports: [
    BrowserModule,
    SchoolRoutingModule,
    CourseRoutingModule
  ],
  providers: [SchoolService, UserService, CourseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
