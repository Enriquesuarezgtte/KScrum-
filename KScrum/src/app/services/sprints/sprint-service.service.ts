import { Injectable } from '@angular/core';
import { Sprint } from "./../../models/details.model";
import { AngularFirestore , AngularFirestoreCollection, DocumentReference } from '@angular/fire/firestore';
import { Observable } from 'rxjs';
import { map , take} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class SprintServiceService {
  private sprt : Observable<Sprint[]>;
  private sprintsCollection: AngularFirestoreCollection<Sprint>;

  constructor(private sprints: AngularFirestore) { 
    this.sprintsCollection = this.sprints.collection<Sprint>('sprints');
    this.sprt = this.sprintsCollection.snapshotChanges().pipe(
      map(actions => {
        return actions.map( a => {
          const data = a.payload.doc.data();
          const id = a.payload.doc.id;

          return { id , ... data};
        });
      })
    );
   }

 
  getUpdate() : Observable<Sprint[]>{
    let items = new Observable<Sprint[]>();
    items = this.sprintsCollection.valueChanges();
    return items;
  }
  createSprint( sprint: any){
    return this.sprintsCollection.add(sprint);
  }
  getSprints(projectId: string) : Observable<Sprint[]>{
    return this.sprt;
  }

getSprint( sprintId: string) : Observable<Sprint>{
  return this.sprintsCollection.doc<Sprint>(sprintId).valueChanges().pipe(
    take(1),
    map(sprint => {
      sprint.id = sprintId;
      return sprint;
    })
  )
}
updateSprint (sprint : Sprint) : Promise<void>{
  return this.sprintsCollection.doc(sprint.id).update({
    sprintId : sprint.id,
    sprintTitle : sprint.title,
    sprintExtraInfo : sprint.extraInfo,
    sprintCreationDate: sprint.creationDate,
    sprintLastDate: sprint.lastDate,
    sprintImagen : sprint.imagen,
    sprintPercentage : sprint.percentage,
    sprintProject : sprint.projectId
  });
}


  deleteSprint(sprintId: string ): Promise <void>{
   return this.sprintsCollection.doc(sprintId).delete();
  }
}
