import { IProjectInterface } from './../../models/Project.model';
import { ProjectServiceService } from './../../services/projects/project-service.service';
import { Router, NavigationExtras } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AlertController, NavController } from '@ionic/angular';




@Component({
  selector: 'app-projects',
  templateUrl: './projects.page.html',
  styleUrls: ['./projects.page.scss'],
})
export class ProjectsPage implements OnInit {
  public projects: Observable<IProjectInterface[]>;


  constructor(public router: Router, public projectsService: ProjectServiceService,
    private alertController: AlertController , public navController : NavController) {

  }


  ngOnInit() {
    console.log("on init re entry ", this.projects);
    this.projects = this.projectsService.getProjects();
  }

  ionViewWillEnter() {
    this.projects = this.projectsService.getProjects();
  }






  async presentDeleteConfirm(projectName: string, projectId: string) {
    const alert = await this.alertController.create({
      header: 'Delete Project',
      message: 'Sure you want to delete the project <strong> ' + projectName + '</strong> ?' +
        '<br></br>' +
        'This will be delete all project info.',
      buttons: [
        {
          text: 'Delete',
          handler: (event) => {
            console.log('project Accept delete');
            this.projectsService.deleteProject(projectId).then(() => {
              console.log("deleted successfully");
            }, (error) => {
              console.log(error);

            })

          }
        }, {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
            console.log('Canceled Delete');
          }
        }
      ]
    });

    await alert.present();
  }


  dispatchUpdateRequest(id: string) {
    let navigationExtras: NavigationExtras = {
      queryParams: {
        projectId: id
      }
    };
    this.navController.navigateForward(['/menu/projectEdition'] , navigationExtras);
    //this.router.navigate(['/menu/projectEdition'], navigationExtras);
  }


  dispatch(id: string) {
    let navigationExtras: NavigationExtras = {
      queryParams: {
        projectId: id
      }
    };
    this.navController.navigateForward(['/menu/first/tabs/projects/details'] , navigationExtras);
    //this.router.navigate(['/menu/projectEdition'], navigationExtras);
  }




}