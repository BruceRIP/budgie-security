import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { Router } from '@angular/router';
import { AccountRequest } from 'src/app/model/AccountRequest';
import { FrontMessage } from 'src/app/model/FrontMessage';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  frontMessage = new FrontMessage();
  email: string;
  message1: string;
  message2: string;
  message3: string;

  constructor(private securityService: SecurityService
            , private router: Router) {    }

  ngOnInit() {  }

  signup(nickname: string, email: string) {
    const accountRequest = new AccountRequest();
    accountRequest.email = email;
    accountRequest.nickname = nickname;
    this.securityService.registerUser(accountRequest)
            .subscribe( (response: any) => {
                this.email = response.email;
                this.message1 = "We sent an email to the following address";
                this.message2 = "to activate your account.";
                this.message3 = "Please verify your inbox";
                this.frontMessage.showAlert = true;
            }, ( error: any ) => {
              this.frontMessage.showAlert = true;
              this.frontMessage.message = error.error.message;
            });
  }
}

