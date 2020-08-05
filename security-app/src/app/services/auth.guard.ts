import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SecurityService } from './security.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements  CanActivate {

  constructor(private securityService: SecurityService
            , private router: Router) {

  }

  canActivate(next: ActivatedRouteSnapshot
            , state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      if (localStorage.getItem('accountSession') != null && JSON.parse(localStorage.getItem('accountSession')).isLogged ) {
        return true;
      }
      this.router.navigate([ '/login' ]);
  }

}
