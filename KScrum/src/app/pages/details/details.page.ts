import { Component, OnInit } from '@angular/core';
import { SprintServiceService } from "src/app/services/sprints/sprint-service.service";
import { Sprint } from "src/app/pages/details/details.model";
import {  NavController } from '@ionic/angular';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
   sprints : Sprint[];

  constructor(private sprintService:SprintServiceService, public navCtrl: NavController) { }

  ngOnInit() {
  this.sprintService.getUserSprint().subscribe(data=>{
    this.sprints=data.map(e=>{
      return {
        id: e.payload.doc.id,
        ...e.payload.doc.data()
      } as Sprint;
    })
  });
  }

  create(sprint: Sprint){
    this.sprintService.createSprint(sprint);
  }
  delete(id:string){
    this.sprintService.deleteSprint(id);
  }
  redirectTo(param: string) {
    this.navCtrl.navigateForward(param);
  }
}
