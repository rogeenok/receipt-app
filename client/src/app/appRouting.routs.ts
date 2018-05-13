import {Routes} from '@angular/router';
import {CheckOperationsComponent} from './user-operations/check-operations/check-operations.component';
import {AddCheckComponent} from './user-operations/check-operations/add-check/add-check.component';
import {ShowCheckItemComponent} from './user-operations/check-operations/show-check/show-check-item.component';
import {Page2Component} from './user-operations/statistics/page2.component';
import {UserOperationsComponent} from "./user-operations/user-operations.component";
import {LoginComponent} from "./login/login/login.component";
import {LoginGuardService} from "./services/loginGuard/login.guard.service";

export const routes: Routes = [
  {
    path: 'user-operations', component: UserOperationsComponent, canActivate: [LoginGuardService],
    children: [{
      path: 'check-operations', component: CheckOperationsComponent,
      children: [
        {path: 'add-check', component: AddCheckComponent},
        {path: 'show-check', component: ShowCheckItemComponent},
        {path: '', redirectTo: 'add-check', pathMatch: 'full'}
      ],
    },
      {path: 'statistics', component: Page2Component},
      {path: '', redirectTo: 'check-operations', pathMatch: 'full'}]
  },
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'}
];
