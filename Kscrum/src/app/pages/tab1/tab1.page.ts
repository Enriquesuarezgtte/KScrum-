import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tab1',
  templateUrl: './tab1.page.html',
  styleUrls: ['./tab1.page.scss'],
})
export class Tab1Page implements OnInit {
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
