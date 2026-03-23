import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBarComponent implements OnInit {

  constructor() { }

  role!:string|null;
  ngOnInit(): void {
    console.log(localStorage.getItem("role"));
    this.role=localStorage.getItem("role");
  }

}