import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {TaskDashboardComponent} from './components/task-dashboard/task-dashboard.component';
import {RoleGuard} from './services/role.guard';
import {UserRole} from './models/user-role';
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {AlreadyLoggedInComponent} from "./components/already-logged-in/already-logged-in.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: HomeComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRoles: [UserRole.ADMIN, UserRole.PROJECT_MANAGER, UserRole.DEVELOPER, UserRole.QA]
    },
    pathMatch: 'full'
  },
  {
    path: 'projects/:projectId/tasks/:taskId',
    component: TaskDashboardComponent,
    canActivate: [RoleGuard],
    data: {
      expectedRoles: [UserRole.PROJECT_MANAGER, UserRole.DEVELOPER, UserRole.QA]
    }
  },
  {
    path: 'already-logged-in',
    component: AlreadyLoggedInComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
