import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';

@Component({
  selector: 'app-recover',
  templateUrl: './recover.component.html',
  styleUrls: ['./recover.component.css']
})
export class RecoverComponent implements OnInit {

  email: string;
  showBanner: boolean;
  error: string;
  message1: string;
  message2: string;

  constructor(private securityService: SecurityService) {
    this.showBanner = false;
   }


  ngOnInit() {
  }

  recover(emailIn: string) {
    this.securityService.resetPassword(emailIn)
      .subscribe( (response: any) => {
        console.log(response.email);
        this.showBanner = true;
        this.email = response.email;
        this.message1 = 'We have sent an email to the following address';
        this.message2 = 'with instructions';
      }, (error: any) => {
        console.log(error.error);
        this.error = error.error.message;
        this.showBanner = true;
      });
  }
}
