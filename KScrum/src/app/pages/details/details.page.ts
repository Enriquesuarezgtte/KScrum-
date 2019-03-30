import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
  public sprints : Array<Object>=[];

  constructor() {
    this.sprints=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"Sprint 1"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"Sprint 2"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"Sprint 3"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"Sprint 4"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"Sprint 5"
     }
   ]
   }

  ngOnInit() {
  }

}
