import { Component, OnInit } from '@angular/core';
import { NavController, ToastController, AlertController } from '@ionic/angular';
import { AuthService } from '../../services/auth/auth.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  public nameInput: string;

  public mailInput: string;

  public passwordInput: string;

  public logoPath = '../../../assets/Scrum.png';
  constructor(public nav: NavController, public auth: AuthService, private toastController: ToastController
  ) {
  }






  ngOnInit() {
  }

  registry() {
    console.log('trying to registry');
    this.auth.registerUserWithEmailAndPassword(this.mailInput, this.passwordInput).then((resolve) => {

      if (resolve === true) {
        this.auth.updateFirebaseName(this.nameInput).then(value => {
          console.log("i am value", value);
          if (value === true) {
            this.nav.navigateForward('menu/first');
            this.mailInput = "";
            this.nameInput = "";
            this.passwordInput = "";
          } else {
            console.log(value);
            this.presentToast('Registry error 1');
          }
        }).catch(value => {
          console.log(value);
          this.presentToast('Registry error 2');
        });
      } else {
        console.log(resolve);
        this.presentToast('Registry error 3');
      }
      ;
    }, (error) => {
      console.log('registry error', error);
      this.presentToast('Registry error 4');
    });

  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }
  goBack() {
    this.nav.back();
  }

}
