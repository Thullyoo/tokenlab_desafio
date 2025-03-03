import { Component, inject, type OnInit } from '@angular/core';
import { EventService } from '../../../services/event/event.service';
import type { EventResponse } from '../../../model/event/EventResponse';
import { CardComponent } from './card/card.component';
import { RouterModule } from '@angular/router';

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
    this.eventService.getEvents().subscribe((res) => res.map((event) => this.events.push(event)));
  }

}
