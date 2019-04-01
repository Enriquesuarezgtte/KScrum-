import { Component, OnInit } from '@angular/core';
import {NavController} from '@ionic/angular';
import {AuthService} from '../../services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  constructor(public nav : NavController , public auth : AuthService) { }
  
  public nameInput : string;

  public mailInput : string;

  public passwordInput : string;


  ngOnInit() {
  }

  public logoPath : string = "../../../assets/Scrum.png";

  registry(){
    console.log("trying to registry")
    this.auth.registerUserWithEmailAndPassword(this.mailInput , this.passwordInput).then((resolve) =>{
      
      console.log("Logged successfully" , resolve);
      this.nav.navigateForward('menu/first');
    } , (error) => {
      console.log("login error" , error);
    });

    this.nav.navigateForward('menu/first');
  }

}
