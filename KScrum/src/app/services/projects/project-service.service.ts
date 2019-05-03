import { IProjectInterface } from './../../models/Project.model';
import { Injectable } from '@angular/core';
import { AngularFirestore , AngularFirestoreCollection, DocumentReference } from '@angular/fire/firestore';
import { Observable } from 'rxjs';
import { map , take} from 'rxjs/operators';




@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {

 
  private projects : Observable<IProjectInterface[]>;
  private  projectsCollection : AngularFirestoreCollection<IProjectInterface>;
  constructor(private projectLogic: AngularFirestore) {
    this.projectsCollection = this.projectLogic.collection<IProjectInterface>('projects');

    this.projects = this.projectsCollection.snapshotChanges().pipe(
        map(actions => {
          return actions.map( a => {
            const data = a.payload.doc.data();
            const id = a.payload.doc.id;

            return { id , ... data};
          });
        })
    );

   }



  getProjects() : Observable<IProjectInterface[]> {

    return this.projects;

  }

  getUpdate() : Observable<IProjectInterface[]>{

    let items = new Observable<IProjectInterface[]>();
     items = this.projectsCollection.valueChanges();
    return items;

  }



  getProject( projectId : string) : Observable<IProjectInterface>{
    return this.projectsCollection.doc<IProjectInterface>(projectId).valueChanges().pipe(
      take(1),
      map( project =>{
        project.id = projectId;
        return project;
      })
    )
  }


  createProject(project: IProjectInterface) : Promise<DocumentReference> {
    return this.projectsCollection.add(project);
  }


  updateProject(project : IProjectInterface) : Promise<void> {
    return this.projectsCollection.doc(project.id).update({
      projectDisplayName: project.projectDisplayName,
      projectPhotoURL: project.projectPhotoURL,
      projectDescription : project.projectDescription,
      projectLanguaje : project.projectLanguaje
    });
  }


  deleteProject(projectId : string) : Promise<void>{
    return this.projectsCollection.doc(projectId).delete();
  }








}
