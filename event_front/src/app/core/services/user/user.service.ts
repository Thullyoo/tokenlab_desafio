import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import type { UserRegisterRequest } from '../../../features/models/user/UserRegisterRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  HttpClient = inject(HttpClient);

  private router = inject(Router);

  private url = "http://localhost:8080/api/user";

  private toastService = inject(ToastrService);

  registerUser(userRequest: UserRegisterRequest) {
    this.HttpClient.post(this.url + "/register", userRequest).subscribe({
      next: () =>{
        this.toastService.success("Usuário cadastrado com sucesso");
        this.router.navigateByUrl("/login");
      }
      ,
      error: () =>{
        alert("Erro ao tentar fazer registro")
      }
    })
  }
}
