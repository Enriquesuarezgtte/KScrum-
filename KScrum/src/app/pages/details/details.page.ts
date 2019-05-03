import { Component, OnInit } from '@angular/core';
import { SprintServiceService } from "src/app/services/sprints/sprint-service.service";
import { Sprint } from "src/app/models/details.model";
import {  NavController } from '@ionic/angular';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable , of } from 'rxjs';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {
   sprints : Observable<Sprint[]>;
  public sprint: Sprint;
  public idProj: string;
 
  
  constructor(private sprintService:SprintServiceService, 
    private activatedRoute: ActivatedRoute,private router: Router, public navCtrl: NavController) { }

  ngOnInit() {
    this.idProj = this.activatedRoute.snapshot.params.get('id');
    this.sprints = this.sprintService.getSprints(this.idProj);

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
