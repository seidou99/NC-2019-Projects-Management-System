import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {TaskDashboardComponent} from './components/task-dashboard/task-dashboard.component';
import {RouterModule} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NewProjectComponent} from './components/new-project/new-project.component';
import {NewUserComponent} from './components/new-user/new-user.component';
import {NewTaskComponent} from './components/new-task/new-task.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TasksTableComponent} from './components/tasks-table/tasks-table.component';
import {HomeNavbarComponent} from './components/home-navbar/home-navbar.component';
import {TaskDashboardNavbarComponent} from './components/task-dashboard-navbar/task-dashboard-navbar.component';
import {TaskDashboardCommentsComponent} from './components/task-dashboard-comments/task-dashboard-comments.component';
import {DatePipe} from '@angular/common';
import {TaskDashboardEditComponent} from './components/task-dashboard-edit/task-dashboard-edit.component';
import {ProjectsComponent} from './components/projects/projects.component';
import {TokenInterceptor} from './interceptors/token.interceptor';
import {ErrorInterceptor} from './interceptors/error.interceptor';
import { ChangeTaskTypeComponent } from './components/change-task-type/change-task-type.component';
import { TaskSortComponent } from './components/task-sort/task-sort.component';
import {NgxSpinnerModule} from "ngx-spinner";
import {Ng4LoadingSpinnerModule} from "ng4-loading-spinner";
import { TaskDashboardAttachmentsComponent } from './components/task-dashboard-attachments/task-dashboard-attachments.component';
import {InputFileModule} from "ngx-input-file";
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AlreadyLoggedInComponent } from './components/already-logged-in/already-logged-in.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    TaskDashboardComponent,
    NewProjectComponent,
    NewUserComponent,
    NewTaskComponent,
    TasksTableComponent,
    HomeNavbarComponent,
    TaskDashboardNavbarComponent,
    TaskDashboardCommentsComponent,
    TaskDashboardEditComponent,
    ProjectsComponent,
    ChangeTaskTypeComponent,
    TaskSortComponent,
    TaskDashboardAttachmentsComponent,
    PageNotFoundComponent,
    AlreadyLoggedInComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule.forRoot(),
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    Ng4LoadingSpinnerModule.forRoot(),
    InputFileModule.forRoot({fileLimit: 5})
  ],
  providers: [
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  entryComponents: [
    NewProjectComponent,
    NewTaskComponent,
    NewUserComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
