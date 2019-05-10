import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../services/security.service';
import { Router } from '@angular/router';
import bcrypt from 'bcryptjs';
import { CryptoService } from 'src/app/services/CryptoService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showError: boolean;
  errorMessage: string;
  constructor(private securityService: SecurityService
            , private router: Router
            , private cryptoService: CryptoService) {
              this.showError = false;
  }

  ngOnInit() {
  }

  login(email: string, password: string) {
    /*const hash = bcrypt.hashSync(password, bcrypt.genSaltSync(10));*/
    const passwordEncrypt = this.cryptoService.set(email, password);
    this.securityService.login(email, passwordEncrypt).subscribe( (response: any) => {
        this.router.navigate(['/home', response.billerID]);
    }, (error: any) => {
      this.showError = true;
      this.errorMessage = error.error.message;
    });
  }
}
