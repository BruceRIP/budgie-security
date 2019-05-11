import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { FrontMessage } from '../../model/FrontMessage';

@Component({
  selector: 'app-recover',
  templateUrl: './recover.component.html',
  styleUrls: ['./recover.component.css']
})
export class RecoverComponent implements OnInit {

  email: string;
  message1: string;
  message2: string;
  frontMessage = new FrontMessage();

  constructor(private securityService: SecurityService) { }


  ngOnInit() {
  }

  recover(emailIn: string) {
    this.securityService.resetPassword(emailIn)
      .subscribe( (response: any) => {
        this.frontMessage.showAlert = true;
        this.email = response.email;
        this.message1 = 'We have sent an email to the following address';
        this.message2 = 'with instructions';
      }, (error: any) => {
        this.frontMessage.showAlert = true;
        this.frontMessage.message = error.error.message;
      });
  }
}
