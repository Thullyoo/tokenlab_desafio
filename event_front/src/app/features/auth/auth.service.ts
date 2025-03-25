import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import type { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, RouterStateSnapshot } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import type { LoginRequest } from '../models/auth/LoginRequest';
import type { TokenResponse } from '../models/auth/TokenResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements CanActivate{

  httpClient = inject(HttpClient);

  private url = "http://localhost:8080/api/auth";

  private router = inject(Router);

  private toastService = inject(ToastrService);

  loginUser(loginRequest: LoginRequest){
    this.httpClient.post<TokenResponse>(this.url + "/login", loginRequest).subscribe({
      next: (res) => {
        this.saveToken(res) 
      } 
      ,
      error: (err) =>
        this.toastService.error("Erro ao tentar fazer login!")
    })
  }

  isTokenExpired(): boolean {
    const tokenData = JSON.parse(localStorage.getItem("token") || '{}');
    if (!tokenData || !tokenData.expirationTime) {
      return true; 
    }
    return Date.now() > tokenData.expirationTime; 
  }

  saveToken(tokenResponse: TokenResponse){
    let token = tokenResponse.token;
    const expirationTime = Date.now() + tokenResponse.expiresAt * 1000;
    localStorage.setItem("token", JSON.stringify({ token, expirationTime }));
    this.router.navigateByUrl('/home');
  }

  removeToken(): void {
    localStorage.removeItem("token");
  }

  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if (this.isTokenExpired()) {
      this.router.navigateByUrl('/login');
      this.removeToken();
      return false;
    }
    return true;
  }

  getToken(): string | null {
    const tokenData = JSON.parse(localStorage.getItem("token") || '{}');

    if (this.isTokenExpired()) {
      this.removeToken(); 
      return null;
    }
    
    return tokenData.token || null; 
  }
}
