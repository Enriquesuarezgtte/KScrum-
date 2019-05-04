import { Component, OnInit, HostListener } from '@angular/core';
import { Platform, NavController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pbis',
  templateUrl: './pbis.page.html',
  styleUrls: ['./pbis.page.scss'],
})
export class PbisPage implements OnInit {
  public tasks: Array<Object> = [];
  public tasks2: Array<Object> = [];
  public tasks3: Array<Object> = [];
  public segment: string;

  constructor(public navCtrl: NavController, private router: Router,
    private platform: Platform) {
    this.segment = "1";

    this.tasks = [
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "               PBI 1",
        "descripcion": "Realizadas 5 de 9 subtareas"

      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 2",
        "descripcion": "Realizadas 1 de 4 tareas"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 3",
        "descripcion": "Realizadas 5 de 6 tareas"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 4",
        "descripcion": "Realizadas 1 de 5 tareas"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 5",
        "descripcion": "Realizadas 7 de 9 tareas"
      }]
    this.tasks2 = [
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "               PBI 6",
        "descripcion": "Tarea sin empezar, pendiente para el día 05/11 "
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 7",
        "descripcion": "Tarea sin empezar, pendiente para el día 07/11 "
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 8",
        "descripcion": "Tarea sin empezar, pendiente para el día 05/12 "
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 9",
        "descripcion": "Tarea sin empezar, pendiente para el día 22/12 "
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 10",
        "descripcion": "Tarea sin empezar, pendiente para el día 25/12"
      }]
    this.tasks3 = [
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "               PBI 11",
        "descripcion": "Tarea terminada el 25/02/19"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 12",
        "descripcion": "Tarea terminada el 28/02/19"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 13",
        "descripcion": "Tarea terminada el 19/02/19"
      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                PBI 14",
        "descripcion": "Tarea terminada el 25/01/19"

      },
      {
        "imagen": "../../../assets/drawable-xhdpi-icon.png",
        "nombre": "                 PBI 15",
        "descripcion": "Tarea terminada el 25/01/18"
      }
    ]

  }

  ngOnInit() {
  }

  redirectTo(param: string) {
    this.navCtrl.navigateBack(param);
  }
  @HostListener('document:ionBackButton', ['$event'])
  private overrideHardwareBackAction($event: any) {
    $event.detail.register(100, async () => {
      this.router.navigateByUrl('/menu/first/tabs/projects/details');
    });
  }

}
