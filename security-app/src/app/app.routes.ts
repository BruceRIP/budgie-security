import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ActivateComponent } from './components/activate/activate.component';
import { HomeComponent } from './components/home/home.component';
import { RecoverComponent } from './components/recover/recover.component';
import { AuthGuard } from './services/auth.guard';

export const ROUTES: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'activate', component: ActivateComponent},
  {path: 'recover', component: RecoverComponent},
  {path: 'home/:id', component: HomeComponent, canActivate: [AuthGuard]},
  {path: '', pathMatch: 'full', component: LoginComponent},
  {path: '**', pathMatch: 'full', component: LoginComponent}
];
