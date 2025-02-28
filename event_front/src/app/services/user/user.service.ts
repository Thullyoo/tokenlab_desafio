import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type { UserRegisterRequest } from '../../model/user/UserRegisterRequest';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  HttpClient = inject(HttpClient);

  private router = inject(Router);

  private url = "http://localhost:8080/api/user";

  registerUser(userRequest: UserRegisterRequest) {
    this.HttpClient.post(this.url + "/register", userRequest).subscribe({
      next: () =>{
        this.router.navigateByUrl("/login");
      }
      ,
      error: () =>{
        alert("Erro ao tentar fazer registro")
      }
    })
  }
}
