import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SecurityService } from '../../services/security.service';
import bcrypt from 'bcryptjs';
import { CryptoService } from '../../services/CryptoService';
import { FrontMessage } from '../../model/FrontMessage';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent {

  email: string;
  id: string;
  code: string;
  type: string;
  frontMessage = new FrontMessage();

  constructor(private activatedRoute: ActivatedRoute
            , private router: Router
            , private securityService: SecurityService
            , private cryptoService: CryptoService) {
      this.type = 'Activate account';
      this.activatedRoute.queryParams.subscribe(( params: any) => {
      this.securityService.getAccountByCodeAndId(params.code, params.billerID)
                      .subscribe( (response: any) => {
                         this.email = response.email;
                         this.code = params.code;
                         this.id = params.billerID;
                         if (params.type === 'reset') {
                          this.type = 'Reset password';
                         }
                      }, ( error: any ) => {
                        this.router.navigate(['/login']);
                        this.frontMessage.showAlert = true;
                        this.frontMessage.message = error.error.message;
                      });
    });
   }

  activate(password: string, repassword: string) {
    if (password !== repassword) {
      this.frontMessage.showAlert = true;
      this.frontMessage.message = 'Your password not match, please verify.';
    } else {
      const hash = bcrypt.hashSync(password, bcrypt.genSaltSync(10));
      const passwordEncrypt = this.cryptoService.set(hash, repassword);
      this.securityService.activateAccount(this.code, this.id, passwordEncrypt, passwordEncrypt)
              .subscribe( (response: any) => {
                this.router.navigate(['/home', response.billerID]);
              }, ( error: any ) => {
                this.frontMessage.showAlert = true;
                this.frontMessage.message = error.error.message;
              });
    }
  }

}
