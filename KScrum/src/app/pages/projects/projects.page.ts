import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.page.html',
  styleUrls: ['./projects.page.scss'],
})
export class ProjectsPage implements OnInit {
  public proyectos : Array<Object>=[];

  constructor() {

    this.proyectos=[
       {
         "imagen":"../../../assets/drawable-xhdpi-icon.png",
          "nombre":"KScrum"
        },
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png", 
        "nombre":"Napa S"
      },
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"Excolnet"
      }
    ]
   }

  ngOnInit() {
  }

}
