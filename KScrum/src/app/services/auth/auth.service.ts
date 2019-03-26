import { Injectable } from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public auth : AngularFireAuth ) { 

   
  }


  loginWithEmailAndPasssword(email : string , password : string){
    return this.auth.auth.signInWithEmailAndPassword(email , password);
  }

  registerUserWithEmailAndPassword(email : string , password : string){
    return this.auth.auth.createUserWithEmailAndPassword(email , password);
  }


 
}
