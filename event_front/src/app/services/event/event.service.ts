import { EventRequest } from './../../model/event/EventRequest';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import type { EventResponse } from '../../model/event/EventResponse';
import type { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  httpClient = inject(HttpClient);

  router = inject(Router);

  private url = "http://localhost:8080/api/event";

  toastService = inject(ToastrService);

  registerEvent(eventRequest: EventRequest){
    this.httpClient.post(this.url + "/register", eventRequest).subscribe({
      next: () =>  {
        this.toastService.success("Evento registrado com sucesso!");
        this.router.navigateByUrl("event-list")
      },
      error: () => {
        this.toastService.error("Erro ao tentar registrar evento!");
      }
        
    })
  }

  getEvents(): Observable<EventResponse[]> {

    return this.httpClient.get<EventResponse[]>(this.url + "/list-events");
  }
  getEventById(id : number) : Observable<EventResponse>{
    return this.httpClient.get<EventResponse>(this.url + `/${id}`);
  }

  isEventCreator(id: number): Observable<boolean>{
    return this.httpClient.get<boolean>(this.url + `/event-creator/${id}`);
  }

  deleteEvent(id : number){
    return this.httpClient.delete(this.url + `/delete/${id}`);
  }

  updateEvent(id:number, event : EventRequest){
    return this.httpClient.put(this.url + `/update/${id}`, event).subscribe();
  }
}