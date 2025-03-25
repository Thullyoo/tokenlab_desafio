import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user/user.service';
import { ToastrService } from 'ngx-toastr';

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

  private toastService = inject(ToastrService);

  form = this.formService.group({
    document: new FormControl<string>("", {
      validators: [
        Validators.required,
        Validators.minLength(11),
        Validators.maxLength(11),
        Validators.pattern(/^\d+$/) 
      ],
      nonNullable: true
    }),
    email: new FormControl<string>("", { validators: [Validators.required, Validators.email], nonNullable: true }),
    name: new FormControl<string>("", { validators: [Validators.required], nonNullable: true }),
    password: new FormControl<string>("", {
      validators: [Validators.required, Validators.minLength(8), Validators.maxLength(20)],
      nonNullable: true
    })
  });




  register() {

    const documentErrors = this.form.get('document')?.errors;
  const emailErrors = this.form.get('email')?.errors;
  const nameErrors = this.form.get('name')?.errors;
  const passwordErrors = this.form.get('password')?.errors;

  if (documentErrors) {
    if (documentErrors['pattern']) {
      this.toastService.error("Documento deve conter apenas números");
      return;
    }
    if (documentErrors['required']) {
      this.toastService.error("Documento é requerido");
      return;
    }
    if (documentErrors['minlength'] || documentErrors['maxlength']) {
      this.toastService.error("Documento deve ter exatamente 11 caracteres");
      return;
    }
  }

  if (emailErrors) {
    if (emailErrors['email']) {
      this.toastService.error("Tipo de e-mail inválido");
      return;
    }
    if (emailErrors['required']) {
      this.toastService.error("Email é requerido");
      return;
    }
  }

  if (nameErrors?.['required']) {
    this.toastService.error("Nome é requerido");
    return;
  }

  if (passwordErrors) {
    if (passwordErrors['minlength']) {
      this.toastService.error("Senha deve conter pelo menos 8 caracteres");
      return;
    }
    if (passwordErrors['maxlength']) {
      this.toastService.error("Senha deve conter no máximo 20 caracteres");
      return;
    }
    if (passwordErrors['required']) {
      this.toastService.error("Senha é requerida");
      return;
    }
  }
    this.userService.registerUser({
      document: this.form.controls.document.value,
      email: this.form.controls.email.value,
      name: this.form.controls.name.value,
      password: this.form.controls.password.value,
    });
  }
}
