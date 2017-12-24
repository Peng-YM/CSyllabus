import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {SchoolDashboardComponent} from "./school-dashboard/school-dashboard.component";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {SchoolDetailComponent} from "./school-detail/school-detail.component";
import {SchoolEditorComponent} from "./school-editor/school-editor.component";
import {UserDetailComponent} from "./user-detail/user-detail.component";
import {CourseDetailComponent} from "./course-detail/course-detail.component";
import {CourseEditorComponent} from "./course-editor/course-editor.component";

const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'dashboard', component: SchoolDashboardComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'school/:id', component: SchoolDetailComponent},
  {path: 'schoolEditor/:id', component: SchoolEditorComponent},
  {path: 'course/:id', component: CourseDetailComponent},
  {path: 'courseEditor/:id', component: CourseEditorComponent},
  {path: 'profile', component: UserDetailComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
