import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';

@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {

  constructor(private projectLogic: AngularFirestore) { }



  getUserProjects(userUUID: string) {
    return this.projectLogic.collection('projects').snapshotChanges();

  }


  createProject(project: any) {
    return this.projectLogic.collection('projects').add(project);
  }








}
