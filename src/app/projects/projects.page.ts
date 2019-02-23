import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.page.html',
  styleUrls: ['./projects.page.scss'],
})
export class ProjectsPage implements OnInit {

  constructor() { }

  rows = [
    {
      "nombre": "Red social",
      "tipo": "Hybryd Application"
      
    },
    {
      "nombre": "Plataforma Empresarial",
      "tipo": "Web Application"
    },
    {
      "nombre": "MarketPlace",
      "tipo": "Web Application"
    },
    {
      "nombre": "Credits App",
      "tipo": "Hybrid Application"
    }
  ];



  ngOnInit() {
  }

}
