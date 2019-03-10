import { Component, OnInit } from '@angular/core';
import{Router} from '@angular/router';
import{MenuController} from '@ionic/angular'

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router : Router,
    public menuController : MenuController
    ) {
      this.menuController.enable(false);
     }

  ngOnInit() {
  }

  signIn(){
    this.router.navigate(['home']);
  }

}
