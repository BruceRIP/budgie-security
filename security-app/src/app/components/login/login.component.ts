import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { Router } from '@angular/router';
import bcrypt from 'bcryptjs';
import { CryptoService } from 'src/app/services/CryptoService';
import { AccountLoginRequest } from '../../model/AccountRequest';
import { FrontMessage } from 'src/app/model/FrontMessage';
import { BillerAccount } from 'src/app/model/BillerAccount';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  frontMessage = new FrontMessage();
  billerAccount = new BillerAccount();

  constructor(private securityService: SecurityService
            , private router: Router
            , private cryptoService: CryptoService) {
  }
  login(email: string, password: string) {
    /*const hash = bcrypt.hashSync(password, bcrypt.genSaltSync(10));*/
    const passwordEncrypt = this.cryptoService.set('93r1QT0666', password);
    const accountLoginRequest = new AccountLoginRequest();
    accountLoginRequest.email = email ;
    accountLoginRequest.password = passwordEncrypt;
    this.securityService.login(accountLoginRequest)
      .subscribe( (response: any) => {
        this.billerAccount.billerID = response.billerID;
        this.billerAccount.email = response.email;
        this.billerAccount.nickname = response.nickname;
        this.billerAccount.isLogged = true;
        localStorage.setItem('accountSession', JSON.stringify(this.billerAccount));
        this.router.navigate(['/home', response.billerID]);
      }, (error: any) => {
        this.frontMessage.showAlert = true;
        this.frontMessage.message = error.error.description;
      });
  }

}

