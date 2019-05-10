import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AccountRequest } from '../components/register/register.component';

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

  registerUser(accountRequest: AccountRequest) {
    return this.callPOST('register/', accountRequest);
  }

  getAccountByCodeAndId(code: string, id: string) {
    const url = `accounts/activate?code=${code}&billerID=${id}`;
    return this.callGET(url);
  }

  activateAccount(code: string, id: string, pass: string, repass: string) {
    const url = `accounts/activate?code=${code}`;
    const activateAccountRequest = {
      billerID: id,
      password: pass,
      repassword: repass
    };
    return this.callPOST(url, activateAccountRequest);
  }

  login(email: string, password: string) {
    const accountRequest = {
      email,
      password
    };
    return this.callPOST('accounts/login', accountRequest);
  }

  getTransactionId(): string {
    let randomTransactionId = '';
    const possible = '0123456789';
    for (let i = 0; i < 9; i++) {
      randomTransactionId += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return randomTransactionId;
  }
}
