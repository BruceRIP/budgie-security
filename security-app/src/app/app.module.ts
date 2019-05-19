import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RouterModule } from '@angular/router';
import { ROUTES } from './app.routes';
import { RegisterComponent } from './components/register/register.component';
import { ActivateComponent } from './components/activate/activate.component';
import { HomeComponent } from './components/home/home.component';
import { CryptoService } from './services/CryptoService';
import { RecoverComponent } from './components/recover/recover.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { MenuComponent } from './components/shared/menu/menu.component';
import { AuthGuard } from './services/auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ActivateComponent,
    HomeComponent,
    RecoverComponent,
    NavbarComponent,
    FooterComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES)
  ],
  providers: [CryptoService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
