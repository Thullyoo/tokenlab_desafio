import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  formService = inject(FormBuilder);

  userService = inject(UserService);

  form = this.formService.group({
    document: new FormControl<String>("", {validators: [Validators.required], nonNullable: true}),
    email: new FormControl<String>("", {validators: [Validators.required], nonNullable: true}),
    name: new FormControl<String>("", {validators: [Validators.required], nonNullable: true}),
    password: new FormControl<String>("", {validators: [Validators.required], nonNullable: true})
  });

  register(){
    this.userService.registerUser({
      document: this.form.controls.document.value,
      email: this.form.controls.email.value,
      name:  this.form.controls.name.value,
      password:  this.form.controls.password.value,
    });
  }
}
