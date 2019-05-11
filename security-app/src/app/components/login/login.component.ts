import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { Router } from '@angular/router';
import bcrypt from 'bcryptjs';
import { CryptoService } from 'src/app/services/CryptoService';
import { AccountLoginRequest } from '../../model/AccountRequest';
import { FrontMessage } from 'src/app/model/FrontMessage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  frontMessage = new FrontMessage();

  constructor(private securityService: SecurityService
            , private router: Router
            , private cryptoService: CryptoService) {
  }
  login(email: string, password: string) {
    /*const hash = bcrypt.hashSync(password, bcrypt.genSaltSync(10));*/
    const passwordEncrypt = this.cryptoService.set(email, password);
    const accountLoginRequest = new AccountLoginRequest();
    accountLoginRequest.email = email ;
    accountLoginRequest.password = passwordEncrypt;
    this.securityService.login(accountLoginRequest)
      .subscribe( (response: any) => {
          this.router.navigate(['/home', response.billerID]);
      }, (error: any) => {
        this.frontMessage.showAlert = true;
        this.frontMessage.message = error.error.message;
      });
  }
}

