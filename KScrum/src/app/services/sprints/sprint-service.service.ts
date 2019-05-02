import { Injectable } from '@angular/core';
import { AngularFirestore } from "@angular/fire/firestore";
@Injectable({
  providedIn: 'root'
})
export class SprintServiceService {

  constructor(private sprints: AngularFirestore) {  }

 getUserSprint(){
   return this.sprints.collection('sprints').snapshotChanges();
  }

  createSprint( sprint: any){
    return this.sprints.collection('sprints').add(sprint);
  }

  deleteSprint(sprintId: string ){
    this.sprints.doc('sprints/'+sprintId).delete();
  }
}
