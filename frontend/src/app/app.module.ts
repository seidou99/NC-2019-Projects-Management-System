import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { TaskDashboardComponent } from './components/task-dashboard/task-dashboard.component';
import {RouterModule} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { NewProjectComponent } from './components/new-project/new-project.component';
import { NewUserComponent } from './components/new-user/new-user.component';
import { NewTaskComponent } from './components/new-task/new-task.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    TaskDashboardComponent,
    NewProjectComponent,
    NewUserComponent,
    NewTaskComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule.forRoot(),
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  entryComponents: [
    NewProjectComponent,
    NewTaskComponent,
    NewUserComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
