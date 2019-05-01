
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

  webApiKey : string;

  constructor(public auth: AngularFireAuth) {
    this.currentUser = new IUser;
     this.webApiKey = '173697716170-75cl00gilu525kpvmt1akoj0qbckcb0b.apps.googleusercontent.com';

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


  logInWithGoogle() : Promise<any> {

     return this.googlePlus.login({'webClientId': this.webApiKey })
      .then(res => {
        console.log(res);
        const googleCredential = firebase.auth.GoogleAuthProvider.credential(res.idToken);
        this.auth.auth.signInWithCredential(googleCredential).then (resolve => {
          console.log(resolve);
          this.currentUser.email = resolve.email;
          this.currentUser.UUID = resolve.uid;
          this.currentUser.displayName = resolve.displayName;
          this.currentUser.imageUrl = resolve.photoURL;

          console.log(this.currentUser);
        }).catch(error => {
          console.log(error);
        });
          return res;
      })
      .catch(err => {
        console.error(err);
        return false;
      });
  }

  logIngWithGitHub()  {
    var provider = new firebase.auth.GoogleAuthProvider();
    firebase.auth().signInWithRedirect(provider).then(function() {
      return firebase.auth().getRedirectResult();
    }).then(function(result) {

      var token = result.credential;
    
      var user = result.user;
      // ...
    }).catch(function(error) {

      var errorCode = error.code;
      var errorMessage = error.message;
    });
    
  } 
}