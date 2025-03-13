import { routes } from './../../../app.routes';
import { Component, inject, type OnInit } from '@angular/core';
import type { EventResponse } from '../../../model/event/EventResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../../services/event/event.service';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { InviteService } from '../../../services/invite/invite.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-event-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './event-page.component.html',
  styleUrl: './event-page.component.scss'
})
export class EventPageComponent implements OnInit {

  private route = inject(ActivatedRoute);
  
  private eventService = inject(EventService);

  private router = inject(Router);

  private formService = inject(FormBuilder);

  private inviteService = inject(InviteService);

  private toastService = inject(ToastrService);

  showUpdate = false;
  showDelete = false;
  showInvite = false;

  event: EventResponse = {
    userCreator: "",
    description: '',
    startTime: '',
    endTime: '',
    members: [],
    id: 0
  } 
  
  private eventId : number | null = null;

  showActions: boolean = false;

  ngOnInit(): void {
    this.eventId = Number(this.route.snapshot.paramMap.get('id'));
  
    this.eventService.getEventById(this.eventId).subscribe({
      next: (res) => {
        this.event = res;
        this.form.patchValue({
          description: this.event.description,
          startTime: this.formatDateForInput(this.event.startTime),
          endTime: this.formatDateForInput(this.event.endTime)
        });
      },
      error: (err) => {
        this.router.navigateByUrl("/home");
      }
    });
  
    this.eventService.isEventCreator(this.eventId).subscribe((res) => {
      this.showActions = res;
    });
  }
  
  formatDateForInput(dateString: string): string {
    const date = new Date(dateString);
    const offset = date.getTimezoneOffset() * 60000; 
    const localDate = new Date(date.getTime() - offset);
    return localDate.toISOString().slice(0, 16); 
  }

  
  form = this.formService.group({
    description: new FormControl<string>("", { validators: [Validators.required], nonNullable: true }),
    startTime: new FormControl<string>("", { validators: [Validators.required], nonNullable: true }),
    endTime: new FormControl<string>("", { validators: [Validators.required], nonNullable: true })
  });

  

   formatDate(dataISO : string) {
    const data = new Date(dataISO);
  
    const dia = String(data.getDate()).padStart(2, '0'); 
    const mes = String(data.getMonth() + 1).padStart(2, '0'); 
    const ano = data.getFullYear();
    const hora = String(data.getHours()).padStart(2, '0');
    const minutos = String(data.getMinutes()).padStart(2, '0');
  
    return `${dia}/${mes}/${ano} ${hora}:${minutos}`;
  }

  formInvite = this.formService.group({
    documentReceiver: new FormControl<string>("", { validators: [Validators.required], nonNullable: true }), 
  })

  showUpdateForm() {
    this.showUpdate = true;
    this.showDelete = false; 
    this.showInvite = false; 
  }

  hideUpdateForm() {
    this.showUpdate = false;
  }

  showDeleteConfirmation() {
    this.showDelete = true;
    this.showUpdate = false; 
    this.showInvite = false;
  }

  hideDeleteConfirmation() {
    this.showDelete = false;
  }

  showInviteForm() {
    this.showInvite = true;
    this.showUpdate = false; 
    this.showDelete = false;  
   }

  confirmInvite() {
    this.inviteService.registerInvite({
      documentReceiver: this.formInvite.controls.documentReceiver.value,
      eventId: this.eventId!
    })
    window.location.reload();
  }

  deleteEvent() {
    this.eventService.deleteEvent(this.eventId!).subscribe();
    this.toastService.info("Evento removido com sucesso!");
    this.router.navigateByUrl("/home");
  }

  update() {    
    this.eventService.updateEvent(this.eventId!, {
      description: this.form.controls.description.value,
      startTime: this.form.controls.startTime.value,
      endTime: this.form.controls.endTime.value
    });
    window.location.reload();
  }
}
