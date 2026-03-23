import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-logout',
    templateUrl: './logout.component.html',
    styleUrls: ['./logout.component.scss']
})
export class LogoutComponent{
    role!: string | null;
    doctorId!: number;
    doctorDetails: any;
    constructor(private authService: AuthService, private router: Router) { }
    userName: string = localStorage.getItem('userName') || 'User';
    logout(): void {
        
    const confirmLogout = window.confirm('Are you sure you want to logout?');
    if (!confirmLogout) {
      return; 
    }
        this.authService.logout();
        this.router.navigate(["/auth"]);
    }

}