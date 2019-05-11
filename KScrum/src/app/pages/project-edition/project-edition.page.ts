import { ToastController } from '@ionic/angular';
import { Component, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../services/projects/project-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { IProjectInterface } from 'src/app/models/Project.model';




@Component({
  selector: 'app-project-edition',
  templateUrl: './project-edition.page.html',
  styleUrls: ['./project-edition.page.scss'],
})
export class ProjectEditionPage implements OnInit {
  public showSpinner: boolean;

  public updateId: string;
  public project: IProjectInterface;
  public pageTitle: string;
  constructor(private projectLogic: ProjectServiceService, private activatedRoute: ActivatedRoute
    , private toastctrl: ToastController, private router: Router) {
    this.showSpinner = false;

    this.activatedRoute.queryParams.subscribe(params => {
      this.updateId = params["projectId"];
      console.log("from params ", this.updateId);
    })
  }

  ngOnInit() {
    if (!this.project) {
      this.project = {} as IProjectInterface;
    }

    if (this.updateId) {
      this.pageTitle = "Update your project";
      this.projectLogic.getProject(this.updateId).subscribe(project => {
        console.log("on init", project);
        this.project = project;
      });
    } else {
      this.pageTitle = "Create a project";



    }

  }



  addProject() {
    this.showSpinner = true;
    this.project.projectPhotoURL = "https://www.joshmorony.com/media/2018/03/ionic4.png";
    this.projectLogic.createProject(this.project).then(() => {
      this.router.navigateByUrl('menu/first/tabs/projects');
      this.showSpinner = false;
      this.presentToast('your project has been added!');
      //Cleanning fields
      this.project = {} as IProjectInterface;
    }, err => {
      this.presentToast("there was an error adding project.");
    });
  }


  updateProject() {
    this.showSpinner = true;
    this.project.projectPhotoURL = "https://www.joshmorony.com/media/2018/03/ionic4.png";
    this.projectLogic.updateProject(this.project).then(() => {
      this.router.navigateByUrl('menu/first/tabs/projects');
      this.showSpinner = false;
      this.presentToast('your project has been updated!');
    }, err => {
      this.presentToast("there was an error updating your project.");
    });
  }

  async presentToast(message: string) {
    const toast = await this.toastctrl.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

}
