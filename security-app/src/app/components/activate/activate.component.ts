import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecurityService } from '../../services/security.service';
import bcrypt from 'bcryptjs';
import { CryptoService } from '../../services/CryptoService';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent implements OnInit {

  email: string;
  id: string;
  code: string;
  showError = false;
  errorMessage: string;

  constructor(private activatedRoute: ActivatedRoute
            , private router: Router
            , private securityService: SecurityService
            , private cryptoService: CryptoService) {
    this.activatedRoute.queryParams.subscribe(( params: any) => {
      this.securityService.getAccountByCodeAndId(params.code, params.billerID)
                      .subscribe( (response: any) => {
                         console.log(response.email);
                         console.log(response);
                         this.email = response.email;
                         this.code = params.code;
                         this.id = params.billerID;
                      }, ( error: any ) => {
                        this.router.navigate(['/login']);
                        this.showError = true;
                        this.errorMessage = error.error.message;
                      });
    });
   }

  ngOnInit() {
  }

  activate(password: string, repassword: string) {
    if (password !== repassword) {
      console.error('password not match');
    } else {
      /*const hash = bcrypt.hashSync(password, bcrypt.genSaltSync(10));*/
      const passwordEncrypt = this.cryptoService.set(this.email, repassword);
      this.securityService.activateAccount(this.code, this.id, passwordEncrypt, passwordEncrypt)
              .subscribe( (response: any) => {
                this.router.navigate(['/home', response.billerID]);
              }, ( error: any ) => {
                console.log(error.error);
                this.showError = true;
                this.errorMessage = error.error.message;
              });
    }
  }

}
