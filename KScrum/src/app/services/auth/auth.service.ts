
import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { Observable } from 'rxjs';
import * as firebase from 'firebase/app';
import { GooglePlus } from '@ionic-native/google-plus/ngx';
import {IUser} from '../../models/User.model';




@Injectable({
  providedIn: 'root'
})


export class AuthService {

  currentUser: IUser;

  webApiKey : string;





  constructor(public auth: AngularFireAuth, public googlePlus: GooglePlus) {
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

  registerUserWithEmailAndPassword(email: string, password: string): Promise<boolean> {
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
    }, (error => {
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
      }).catch(err => {
        console.error(err);
        return false;
      });
  }

  logIngWithGitHub()   {
   
/*
    this.auth.auth.signInWithRedirect(provider).then(() =>{
      return this.auth.auth.getRedirectResult();
    }).then( result => {
      // This gives you a Google Access Token.ro
      // You can use it to access the Google API.
      console.log(result);
      var token = result.user.getIdToken()
      .then(token => {
        console.log("Token:", token);
      })
      .catch(error => {
        console.log("Error getting token:", error);
      });;
      var user = result.user;
      // The signed-in user info.
    }).catch(error => {
      // ...
      var errorCode = error.code;
      var errorMessage = error.message;
    });
  } */
  var provider = new firebase.auth.GithubAuthProvider();
  provider.addScope('repo');
this.auth.auth.signInWithRedirect(provider);
  }    
}