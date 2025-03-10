import { isAccepeted } from './../../model/invite/InviteAccepted';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import type { InviteRequest } from '../../model/invite/InviteRequest';
import { InviteResponse } from '../../model/invite/InviteResponse';

@Injectable({
  providedIn: 'root'
})
export class InviteService {
  private httpClient = inject(HttpClient);

  private url = "http://localhost:8080/api/invite";

  authService = inject(AuthService);

  registerInvite(inviteRequest: InviteRequest){

    const token = this.authService.getToken()

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.post(this.url + "/register", inviteRequest, {headers}).subscribe();
  }

  getInviteByUser(){
    const token = this.authService.getToken()

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.get<InviteResponse[]>(this.url + "/list-invite", {headers});
  }

  acceptedInvite(isAccepeted : isAccepeted){
    const token = this.authService.getToken()

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    return this.httpClient.post(this.url + "/isAccepted", isAccepeted,  {headers}).subscribe();
  }
}
