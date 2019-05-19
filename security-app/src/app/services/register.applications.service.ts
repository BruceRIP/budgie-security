import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterApplicationsService {

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

  register(id: string, name: string) {
    return this.callPOST(`register/authorization/${id}?applicationName=${name}`, { });
  }

  getAllApplications(id: string) {
    return this.callGET(`clients/authentication/${id}/all`);
  }
}
