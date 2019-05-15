import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private httpClient: HttpClient) { }

  callPOST(query: string, data: any) {
    const url = `/budgie/${query}`;
    const transactionId = this.getTransactionId();
    const headers = new HttpHeaders({
      'Authorization': 'bearer 1dd8103c-4690-4bcf-8654-0cf0a97ce77a',
      'Content-Type': 'application/json',
      'transactionId': transactionId
    });
    return this.httpClient.post(url, data, {headers});
  }

  callGET(query: string) {
    const url = `/budgie/${query}`;
    const transactionId = '123456789';
    const headers = new HttpHeaders({
      'Authorization': 'bearer 1dd8103c-4690-4bcf-8654-0cf0a97ce77a',
      'transactionId': transactionId
    });
    return this.httpClient.get(url, {headers});
  }

  getTransactionId(): string {
    let randomTransactionId = '';
    const possible = '0123456789';
    for (let i = 0; i < 9; i++) {
      randomTransactionId += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return randomTransactionId;
  }

  registerUser(accountRequest: any) {
    return this.callPOST('register', accountRequest);
  }

  getAccountByCodeAndId(code: string, id: string) {
    const url = `accounts/activate?code=${code}&billerID=${id}`;
    return this.callGET(url);
  }

  activateAccount(code: string, id: string, pass: string, repass: string) {
    const url = `accounts/activate?code=${code}&billerID=${id}`;
    const activateAccountRequest = {
      password: pass,
      repassword: repass
    };
    return this.callPOST(url, activateAccountRequest);
  }

  login(accountLoginRequest: any) {
    return this.callPOST('accounts/login', accountLoginRequest);
  }

  resetPassword(email: string) {
    const accountRequest = {
      email
    };
    return this.callPOST(`register/reset`, accountRequest);
  }
}
