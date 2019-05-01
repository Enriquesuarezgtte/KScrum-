
import {AuthService} from '../../services/auth/auth.service';
import { FirstWithTabsPage} from '../first-with-tabs/first-with-tabs.page';
import {NavController} from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  public mailInput: string;

  public passwordInput: string;

  public currentUser: IUser;
  public logoPath = '../../../assets/Scrum.png';

  constructor(private authService: AuthService, public navCtrl: NavController, private toastController: ToastController) { }




  ngOnInit() {

  }


  login() {
    this.authService.loginWithEmailAndPasssword(this.mailInput, this.passwordInput).then(value => {
      if (value === true) {
        this.redirectTo('/menu/first');
        this.mailInput = null;
        this.passwordInput = null;
      } else {
        this.presentToast('Login failure');
      }
    }, ((error) => {
      this.presentToast('Login failure');
    }));


  }




  redirectTo(param: string) {
    this.navCtrl.navigateForward(param);
  }


}

import {AuthService} from '../../services/auth/auth.service';
import { FirstWithTabsPage} from '../first-with-tabs/first-with-tabs.page';
import {NavController} from '@ionic/angular';
import { Component, OnInit } from '@angular/core';
import { AuthService} from '../../services/auth/auth.service';
import { ToastController } from '@ionic/angular';
import { NavController } from '@ionic/angular';
import {IUser} from '../../models/User.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  public mailInput: string;

  public passwordInput: string;

  public currentUser: IUser;
  public logoPath = '../../../assets/Scrum.png';

  constructor(private authService: AuthService, public navCtrl: NavController, private toastController: ToastController) { }




  ngOnInit() {

  }


  login() {
    this.authService.loginWithEmailAndPasssword(this.mailInput, this.passwordInput).then(value => {
      if (value === true) {
        this.redirectTo('/menu/first');
        this.mailInput = null;
        this.passwordInput = null;
      } else {
        this.presentToast('Login failure');
      }
    }, ((error) => {
      this.presentToast('Login failure');
    }));


  }




  redirectTo(param: string) {
    this.navCtrl.navigateForward(param);
  }


  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

  loginWithGoogle() {
    this.authService.logInWithGoogle().then(data => {
      if (data.idToken != null) {
        this.redirectTo('/menu/first');
      } else {
        this.presentToast('Login failure');
      }

    }).catch(error => {
      console.log(error);
      this.presentToast('Login failure');
    });
  }


  loginWithGitHub() {
    this.authService.logIngWithGitHub();
  }

}
