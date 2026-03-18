import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBarComponent implements OnInit {
  role: string | null = null;

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
  }
}