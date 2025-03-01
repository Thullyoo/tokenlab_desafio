import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterEventComponent {

  formService = inject(FormBuilder);
  
  eventService = inject(EventService);

  form = this.formService.group({
    description: new FormControl<String>("", {validators: [Validators.required], nonNullable: true}),
    startTime: new FormControl<Date>(new Date(), {validators: [Validators.required], nonNullable: true}),
    endTime: new FormControl<Date>(new Date(), {validators: [Validators.required], nonNullable: true})
  });

  register(){
    this.eventService.registerEvent({
      description: this.form.controls.description.value,
      startTime: this.form.controls.startTime.value,
      endTime: this.form.controls.endTime.value
    });
  };

}
