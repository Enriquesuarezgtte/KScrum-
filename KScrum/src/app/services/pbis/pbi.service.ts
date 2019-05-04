import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection, DocumentReference } from '@angular/fire/firestore';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { IPbi } from '../../models/Pbi.model';

@Injectable({
  providedIn: 'root'
})
export class PbiService {


  private pbis: Observable<IPbi[]>;
  private pbisCollection: AngularFirestoreCollection<IPbi>;
  constructor(private pbiLogic: AngularFirestore) {
    this.pbisCollection = this.pbiLogic.collection<IPbi>('pbis');

    this.pbis = this.pbisCollection.snapshotChanges().pipe(
      map(actions => {

        return actions.map(p => {

          const id = p.payload.doc.id;
          const data = p.payload.doc.data();

          return { id, ...data };
        });

      })
    );
  }





  getPbis(): Observable<IPbi[]> {
    return this.pbis;
  }

  getPbi(pbiId: string): Observable<IPbi> {
    return this.pbisCollection.doc<IPbi>(pbiId).valueChanges().pipe(
      take(1),
      map(pbi => {
        pbi.id = pbiId;
        return pbi;
      })
    );
  }


  createPbi(Pbi: IPbi): Promise<DocumentReference> {

    return this.pbisCollection.add(Pbi);
  }


  updatePbi(updatedPbi: IPbi): Promise<void> {
    return this.pbisCollection.doc(updatedPbi.id).update({
      projectId: updatedPbi.projectId,
      PbiTitle: updatedPbi.PbiTitle,
      PbiDescription: updatedPbi.PbiDescription,
      responsableId: updatedPbi.responsableId,
      statusId: updatedPbi.statusId
    });
  }


  deletePbi(pbiId: string): Promise<void> {
    return this.pbisCollection.doc(pbiId).delete();
  }
}
