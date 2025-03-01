import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import type { EventRequest } from '../../model/event/EventRequest';
import { AuthService } from '../auth/auth.service';
import type { EventResponse } from '../../model/event/EventResponse';
import type { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  httpClient = inject(HttpClient);

  router = inject(Router);

  private url = "http://localhost:8080/api/event";

  authService = inject(AuthService);

  registerEvent(eventRequest: EventRequest){

    const token = this.authService.getToken()

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
    
    this.httpClient.post(this.url + "/register", eventRequest, {headers}).subscribe({
      next: () =>  {
        this.router.navigateByUrl("event-list")
      },
      error: () => {
        alert("Erro ao tentar registar evento");
      }
        
    })
  }

  getEvents(): Observable<EventResponse[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
  
    return this.httpClient.get<EventResponse[]>(this.url + "/list-events", { headers });
  }
  }
