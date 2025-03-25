import { Component, inject } from '@angular/core';
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  formService = inject(FormBuilder);

  authservice = inject(AuthService);

  form = this.formService.group({
    document: new FormControl<String>("", {validators: [Validators.required], nonNullable: true}),
    password: new FormControl<String>("", {validators: [Validators.required], nonNullable: true})
  });

  login(){
    this.authservice.loginUser({
      document: this.form.controls.document.value,
      password: this.form.controls.password.value
    });
  }
}

