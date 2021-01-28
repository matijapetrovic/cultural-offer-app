import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';

import { AuthenticationService } from '../../authentication.service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.scss']
})
export class ActivationComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private authenticationService: AuthenticationService,
    public messageService: MessageService,
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.params.id;
    this.authenticationService.activate(id)
      .subscribe(() => {
        this.messageService.add({
          severity: 'success', summary: 'Account activation', detail: 'You have successfully activated your email!'
        });
    });
    this.router.navigate(['/map']);
  }

}
