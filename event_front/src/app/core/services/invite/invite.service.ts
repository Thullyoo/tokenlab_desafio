import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type { InviteRequest } from '../../../features/models/invite/InviteRequest';
import type { isAccepeted } from '../../../features/models/invite/InviteAccepted';
import type { InviteResponse } from '../../../features/models/invite/InviteResponse';


@Injectable({
  providedIn: 'root'
})
export class InviteService {
  private httpClient = inject(HttpClient);

  private url = "http://localhost:8080/api/invite";

  registerInvite(inviteRequest: InviteRequest){
    return this.httpClient.post(this.url + "/register", inviteRequest).subscribe();
  }

  getInviteByUser(){
    return this.httpClient.get<InviteResponse[]>(this.url + "/list-invite");
  }

  acceptedInvite(isAccepeted : isAccepeted){
    return this.httpClient.post(this.url + "/isAccepted", isAccepeted).subscribe();
  }
}
