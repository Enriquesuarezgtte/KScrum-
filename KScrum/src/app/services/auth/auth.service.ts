
import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { Observable } from 'rxjs';
import * as firebase from 'firebase/app';
import { GooglePlus } from '@ionic-native/google-plus/ngx';
import { IUser } from '../../models/User.model';
import { webApikey } from '../../../environments/environment';




@Injectable({
  providedIn: 'root'
})


export class AuthService {

  currentUser: IUser;





  constructor(public auth: AngularFireAuth, public googlePlus: GooglePlus) {
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

  registerUserWithEmailAndPassword(email: string, password: string): Promise<boolean> {
    return this.auth.auth.createUserWithEmailAndPassword(email, password).then(resolve => {
      if (resolve != null) {

        this.currentUser.email = resolve.user.email;
        this.currentUser.UUID = resolve.user.uid;


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


  logInWithGoogle(): Promise<any> {

    return this.googlePlus.login({ 'webClientId': webApikey })
      .then(res => {
        console.log(res);
        const googleCredential = firebase.auth.GoogleAuthProvider.credential(res.idToken);
        this.auth.auth.signInWithCredential(googleCredential).then(resolve => {
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
  

  logIngWithGitHub() {
    const provider = new firebase.auth.GithubAuthProvider();

    console.log('Estoy en el provider');
    this.auth.auth.signInWithPopup(provider)
      .then(result => {
        var user = result.user;
        console.log(user);
      })
      .catch(err => {
        console.log(err);
      });
  }



  updateFirebaseName(displayNameParam: string): Promise<boolean> {
    return this.auth.auth.currentUser.updateProfile({
      displayName: displayNameParam
    }).then(() => {
      this.currentUser.displayName = displayNameParam;
      console.log('updated user Name  Successfully');
      return true;
    }).catch((error) => {
      console.log('update user Name  failed', error);
      return false;
    });


  }

  updateFirebaseUser(displayNameParam: string, userPhoto: string): Promise<boolean> {

    return this.auth.auth.currentUser.updateProfile({
      displayName: displayNameParam,
      photoURL: userPhoto
    }).then(() => {
      this.currentUser.displayName = displayNameParam;
      this.currentUser.imageUrl = userPhoto;
      console.log('updated user data Successfully');
      return true;
    }).catch((error) => {
      console.log('update userName  failed', error);
      return false;
    });


  }

  updateFirebasePhoto(userPhoto: string): Promise<boolean> {

    return this.auth.auth.currentUser.updateProfile({
      photoURL: userPhoto
    }).then(() => {
      this.currentUser.imageUrl = userPhoto;
      console.log('updated photo  Successfully');
      return true;
    }).catch(() => {
      console.log('update user Name  failed');
      return false;
    });


  }



}
