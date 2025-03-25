import { Component, inject, type OnInit } from '@angular/core';

import { CardComponent } from './card/card.component';
import { RouterModule } from '@angular/router';
import type { EventResponse } from '../../../../features/models/event/EventResponse';
import { EventService } from '../../../../core/services/event/event.service';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CardComponent, RouterModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss'
})
export class ListComponent implements OnInit{

  events : EventResponse[] = []
  
  private eventService = inject(EventService);

  ngOnInit(): void {
    this.eventService.getEvents().subscribe((res) =>{
      this.events = res;
      res.sort((a,b) => new Date(b.startTime).getTime() - new Date(a.startTime).getTime());
    })
  }

}
