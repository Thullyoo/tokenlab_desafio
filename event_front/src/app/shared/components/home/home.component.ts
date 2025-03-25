import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../features/auth/auth.service';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  private authService = inject(AuthService);
  
  private router = inject(Router);

  logout(){
    this.authService.removeToken();
    this.router.navigateByUrl("/login");
  }

}
