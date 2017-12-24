import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {SchoolDashboardComponent} from './school-dashboard/school-dashboard.component';
import {SchoolDetailComponent} from './school-detail/school-detail.component';
import {CourseDetailComponent} from './course-detail/course-detail.component';
import {UserDetailComponent} from './user-detail/user-detail.component';
import {SchoolService} from './school.service';
import {UserService} from './user.service';
import {CourseService} from './course.service';
import {LoginService} from "./login.service";
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';
import {RegistrationComponent} from './registration/registration.component';
import {AppRoutingModule} from './/app-routing.module';
import {SchoolEditorComponent} from './school-editor/school-editor.component';
import {CourseEditorComponent} from './course-editor/course-editor.component';
import {CookieService} from "ngx-cookie-service";
import {PdfViewerModule} from "ng2-pdf-viewer"
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {FileSelectDirective} from 'ng2-file-upload';

@NgModule({
  declarations: [
    AppComponent,
    SchoolDashboardComponent,
    SchoolDetailComponent,
    CourseDetailComponent,
    UserDetailComponent,
    LoginComponent,
    RegistrationComponent,
    SchoolEditorComponent,
    CourseEditorComponent,
    FileSelectDirective
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    PdfViewerModule
  ],
  providers: [CookieService, SchoolService, UserService, CourseService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

platformBrowserDynamic().bootstrapModule(AppModule);
