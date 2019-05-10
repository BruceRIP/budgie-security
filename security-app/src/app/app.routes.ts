import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ActivateComponent } from './components/activate/activate.component';
import { HomeComponent } from './components/home/home.component';

export const ROUTES: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'activate', component: ActivateComponent},
  {path: 'home/:nickname', component: HomeComponent},
  {path: '', pathMatch: 'full', component: LoginComponent},
  {path: '**', pathMatch: 'full', component: LoginComponent}
];
