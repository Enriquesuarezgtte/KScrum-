
import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { Observable } from 'rxjs';



export class IUser {
  email: string;
  UUID: string;
  displayName: string;
  imageUrl: string;

}



@Injectable({
  providedIn: 'root'
})


export class AuthService {

  currentUser: IUser;


  constructor(public auth: AngularFireAuth) {
    this.currentUser = new IUser;

  }



  getCurrentUser() {
    return this.currentUser;
  }

  loginWithEmailAndPasssword(email: string, password: string): Promise<boolean> {
    return this.auth.auth.signInWithEmailAndPassword(email, password).then(resolve => {
      if (resolve != null) {

        this.currentUser.email = resolve.user.email;
        this.currentUser.UUID = resolve.user.uid;
        this.currentUser.displayName = resolve.user.displayName;
        this.currentUser.imageUrl = resolve.user.photoURL;

        return true;
      } else {
        return false;
      }
    }, (error) => {
      console.error(error);
      return false;


    });
  }

  registerUserWithEmailAndPassword(email: string, password: string) : Promise< boolean>  {
    return this.auth.auth.createUserWithEmailAndPassword(email, password).then(resolve => {
      if (resolve != null) {

        this.currentUser.email = resolve.user.email;
        this.currentUser.UUID = resolve.user.uid;
        this.currentUser.displayName = resolve.user.displayName;
        this.currentUser.imageUrl = resolve.user.photoURL;

        return true;
      } else {
        return false;
      }
    } ,  (error => {
      console.error(error);
      return false;
    }));
  }

  logOut() {
    this.currentUser = null;
    return this.auth.auth.signOut();
  }



}
