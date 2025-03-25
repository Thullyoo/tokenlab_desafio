import { RegisterEventComponent } from './shared/components/event/register/register.component';
import { Router, Routes } from '@angular/router';
import { HomeComponent } from './shared/components/home/home.component';
import { LoginComponent } from './shared/components/login/login.component';
import { AuthService } from './features/auth/auth.service';
import { RegisterComponent } from './shared/components/register/register.component';
import { ListComponent } from './shared/components/event/list/list.component';
import { EventPageComponent } from './shared/components/event/event-page/event-page.component';
import { ListInviteComponent } from './shared/components/invite/list-invite/list-invite.component';

export const routes: Routes = [
  {
    path: "login",
    component: LoginComponent,
    canActivate: [
      () => {
        const router = new Router();
        const authService = new AuthService();
        if (authService.isTokenExpired()) {
          return true;  
        } else {
          return router.navigateByUrl('/home');
        }
      }
    ]
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [
      () => {
        const router = new Router();
        const authService = new AuthService();
        if (authService.isTokenExpired()) {
          return true;  
        } else {
          return router.navigateByUrl('/home');
        }
      }
    ]
  },
  {
    path: "list-events",
    component:ListComponent,
    canActivate: [AuthService]
  },
  {
    path: "register-event",
    component:RegisterEventComponent,
    canActivate: [AuthService]
  },
  {
    path: "event/:id",
    component: EventPageComponent,
    canActivate: [AuthService]
  },
  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthService]
    },
    {
    path: "list-invite",
    component: ListInviteComponent,
    canActivate: [AuthService]
  },
  { 
    path: '', 
    redirectTo: '/home', 
    pathMatch: 'full' 
  },
  { 
    path: '**', 
    redirectTo: '/home' 
  },
];
