import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  accountRequest: AccountRequest;
  email: string;
  showBanner: boolean;
  error: string;
  message1: string;
  message2: string;
  message3: string;

  constructor(private securityService: SecurityService
            , private router: Router) {
    this.showBanner = false;
  }

  ngOnInit() {  }

  signup(nickname: string, email: string) {
    console.log(`Signing up to user ${nickname} and email ${email}`);
    this.accountRequest = new AccountRequest();
    this.accountRequest.email = email;
    this.accountRequest.nickname = nickname;
    this.securityService.registerUser(this.accountRequest)
            .subscribe( (response: any) => {
                /*this.router.navigate(['/home', response.nickname]);*/
                this.showBanner = true;
                this.email = response.email;
                this.message1 = "We sent an email to the following address";
                this.message2 = "to activate your account.";
                this.message3 = "Please verify your inbox";
            }, ( error: any ) => {
                console.log(error.error);
                this.error = error.error.message;
                this.showBanner = true;
            });
  }
}

export class AccountRequest {
  nickname: string;
  email: string;
}
