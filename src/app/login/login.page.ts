import { Component, OnInit } from '@angular/core';
import { FingerprintAIO } from '@ionic-native/fingerprint-aio';

import { LoadingController } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private faio: FingerprintAIO,public loadingController: LoadingController) { }

  ngOnInit() {
    
  }
  login(){
    this.faio.show({
      clientId: 'Fingerprint-Demo',
      clientSecret: 'password', //Only necessary for Android
      disableBackup:true,  //Only for Android(optional)
      localizedFallbackTitle: 'Use Pin', //Only for iOS
      localizedReason: 'Please authenticate' //Only for iOS
  })
  .then((result: any) => this.presentLoadingWithOptions())
  .catch((error: any) => console.log(error));
  }
  async presentLoadingWithOptions() {
    const loading = await this.loadingController.create({
      spinner: null,
      duration: 5000,
      message: 'Please wait...',
      translucent: true,
      cssClass: 'custom-class custom-loading'
    });
    return await loading.present();
  }
}
