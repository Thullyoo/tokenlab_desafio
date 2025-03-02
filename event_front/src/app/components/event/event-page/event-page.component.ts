import { routes } from './../../../app.routes';
import { Component, inject, type OnInit } from '@angular/core';
import type { EventResponse } from '../../../model/event/EventResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-event-page',
  standalone: true,
  imports: [],
  templateUrl: './event-page.component.html',
  styleUrl: './event-page.component.scss'
})
export class EventPageComponent implements OnInit {

  private route = inject(ActivatedRoute);
  
  private eventService = inject(EventService);

  private router = inject(Router);

  event: EventResponse = {
    userCreator: "",
    description: '',
    startTime: '',
    endTime: '',
    members: [],
    id: 0
  } 
  
  private eventId : number | null = null;

  isEventCreator : boolean = false;

  ngOnInit(): void {
    this.eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getEventById(this.eventId).subscribe(
      {
        next: (res) => {
          this.event = res;
        }, 
        error: (err) =>{
          this.router.navigateByUrl("/home");
        }
      }
    )
    this.eventService.isEventCreator(this.eventId).subscribe((res) => {
      console.log(res);
      this.isEventCreator = res;
    })
  }

   formatDate(dataISO : string) {
    const data = new Date(dataISO);
  
    const dia = String(data.getDate()).padStart(2, '0'); 
    const mes = String(data.getMonth() + 1).padStart(2, '0'); 
    const ano = data.getFullYear();
    const hora = String(data.getHours()).padStart(2, '0');
    const minutos = String(data.getMinutes()).padStart(2, '0');
  
    return `${dia}/${mes}/${ano} ${hora}:${minutos}`;
  }
}
