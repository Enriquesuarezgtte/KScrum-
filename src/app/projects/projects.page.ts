import { Component, OnInit } from '@angular/core';
import { Router} from  '@angular/router';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.page.html',
  styleUrls: ['./projects.page.scss'],
})
export class ProjectsPage implements OnInit {

  constructor(private router : Router) { }

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

projectRedir(){
  this.router.navigate(['sproject']);
}

}
