import { Component, OnInit  } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import { FirstWithTabsPage} from '../first-with-tabs/first-with-tabs.page';
import {NavController} from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  private mailInput : string;

  private passwordInput : string;


  public logoPath : string = "../../../assets/Scrum.png";

  constructor(private authService : AuthService , public navCtrl : NavController) { }


  

  ngOnInit() {
  
  }


login(){

this.authService.loginWithEmailAndPasssword(this.mailInput , this.passwordInput).then( resolve =>{
  console.log(resolve  , " in page")
  this.navCtrl.navigateForward('/menu/first');
}, (error) => {
  console.error(error ,  " in page")
}) 

 // this.navCtrl.navigateForward('/menu/first');
}


redirectTo(param : string){
  this.navCtrl.navigateForward(param);
}


}
