import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tab2',
  templateUrl: './tab2.page.html',
  styleUrls: ['./tab2.page.scss'],
})
export class Tab2Page implements OnInit {
  public tasks : Array<Object>=[];
  public tasks2 : Array<Object>=[];
  public tasks3 : Array<Object>=[];

  constructor() { 
    this.tasks=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"               PDI 1"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PDI 2"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 3"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 4"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PDI 5"
     }]
     this.tasks2=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"               PDI 6"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PDI 7"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 8"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 9"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PDI 10"
     }]
     this.tasks3=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"               PDI 11"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PDI 12"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 13"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PDI 14"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PDI 15"
     }
   ]
   
  }
 
  ngOnInit() {
  }

}
