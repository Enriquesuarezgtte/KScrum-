import { Component, OnInit } from '@angular/core';
import { NavController, ToastController, AlertController} from '@ionic/angular';
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
          } else {
            this.presentToast('Registry error');
          }
        }).catch(value => {
          console.log(value);
          this.presentToast('Registry error');
        });
      } else {
        this.presentToast('Registry error');
      }
      ;
    }, (error) => {
      console.log('registry error', error);
      this.presentToast('Registry error');
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
