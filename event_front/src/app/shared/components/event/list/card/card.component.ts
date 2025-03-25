import { Component, input } from '@angular/core';
import type { EventResponse } from '../../../../model/event/EventResponse';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {

  event = input.required<EventResponse>();

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
