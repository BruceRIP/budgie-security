import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationClient } from 'src/app/model/ApplicationClient';
import { FrontMessage } from 'src/app/model/FrontMessage';
import { RegisterApplicationsService } from 'src/app/services/register.applications.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: []
})
export class HomeComponent implements OnInit {

  billerID: string;
  applicationsClient: ApplicationClient[] = [];
  appClient = new ApplicationClient();
  frontMessage = new FrontMessage();
  applicationType: string;

  constructor(private activatedRoute: ActivatedRoute
            , private registerApplicationsService: RegisterApplicationsService) {
    this.activatedRoute.params.subscribe( params => {
      this.billerID = params['id'];
    });
    if (localStorage.getItem('applicationsRegistered') == null) {
      registerApplicationsService.getAllApplications(this.billerID)
        .subscribe( (response: any[]) => {
          if (response.length < 0) {
            console.log(' no theres more');
          } else {
          localStorage.setItem('applicationsRegistered', JSON.stringify(response));
          this.applicationsClient = JSON.parse(localStorage.getItem('applicationsRegistered'));
          }
        }, (error: any) => {
          this.frontMessage.showAlert = true;
          this.frontMessage.message = error.error;
        });
    } else {
      this.applicationsClient = JSON.parse(localStorage.getItem('applicationsRegistered'));
    }
  }

  ngOnInit() { }

  registerApplication(name: string) {
    this.registerApplicationsService.register(this.billerID, name)
        .subscribe( (response: any) => {
            this.appClient.clientId = response.clientId;
            this.appClient.clientSecret = response.clientSecret;
            this.appClient.applicationName = name;
            if (localStorage.getItem('applicationsRegistered') == null) {
              localStorage.setItem('applicationsRegistered', JSON.stringify(this.appClient));
            } else {
              this.applicationsClient = JSON.parse(localStorage.getItem('applicationsRegistered'));
              this.applicationsClient.push(this.appClient);
              localStorage.setItem('applicationsRegistered', JSON.stringify(this.applicationsClient));
              this.applicationsClient = JSON.parse(localStorage.getItem('applicationsRegistered'));
            }

        }, (error: any) => {
          this.frontMessage.showAlert = true;
          this.frontMessage.message = error.error.message;
        });
  }

  selectApplication(type: string) {
    this.applicationType = type;
  }

}
