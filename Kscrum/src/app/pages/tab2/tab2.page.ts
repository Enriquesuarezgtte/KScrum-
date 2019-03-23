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
         "nombre":"               PBI 1"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PBI 2"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 3"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 4"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PBI 5"
     }]
     this.tasks2=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"               PBI 6"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PBI 7"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 8"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 9"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PBI 10"
     }]
     this.tasks3=[
      {
        "imagen":"../../../assets/drawable-xhdpi-icon.png",
         "nombre":"               PBI 11",
         "descripcion":"Tarea terminada el 25/02/19"
       },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png", 
       "nombre":"                 PBI 12",
       "descripcion":"Tarea terminada el 28/02/19"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 13",
        "descripcion":"Tarea terminada el 19/02/19"
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                PBI 14",
        "descripcion":"Tarea terminada el 25/01/19"
        
     },
     {
       "imagen":"../../../assets/drawable-xhdpi-icon.png",
        "nombre":"                 PBI 15",
        "descripcion":"Tarea terminada el 25/01/18"
     }
   ]
   
  }
 
  ngOnInit() {
  }

}
