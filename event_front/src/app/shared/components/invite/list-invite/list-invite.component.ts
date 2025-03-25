import { Component, inject, type OnInit } from '@angular/core';
import type { InviteResponse } from '../../../../features/models/invite/InviteResponse';
import { InviteService } from '../../../../core/services/invite/invite.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-invite',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './list-invite.component.html',
  styleUrl: './list-invite.component.scss'
})
export class ListInviteComponent implements OnInit {

  private inviteService = inject(InviteService);

  listInvite : InviteResponse[] = []

  private router = inject(Router);

  ngOnInit(): void {
    this.inviteService.getInviteByUser().subscribe({
      next: (res) => {
        this.listInvite = res;
      },
      error: (err) =>{
        console.error(err);
      }
    })
  }
  
  acceptInvite(inviteId: number) {
    this.inviteService.acceptedInvite({
      isAccepted: true,
      inviteId : inviteId
    })
    this.router.navigateByUrl("/list-events");
  }
  declineInvite(inviteId: number) {
    this.inviteService.acceptedInvite({
      isAccepted: true,
      inviteId : inviteId
    });
    window.location.reload();
  }
}
