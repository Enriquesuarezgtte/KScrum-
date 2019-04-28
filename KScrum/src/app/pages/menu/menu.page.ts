import { LoginPage } from './../login/login.page';
import { Component, OnInit } from '@angular/core';
import { Router, RouterEvent } from '@angular/router';
import {AuthService} from '../../services/auth/auth.service';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {
  selectedPath = '';

  pages = [
    {
      title: 'Projects',
      icon: 'folder-open',
      url: '/menu/first'
    }
    , {
      title: 'Manage Projects',
      children: [
        {
          title: 'Create Project',
          url: '/menu/second',
          icon: 'folder-open'
        },
        {
          title: 'Manage Projects',
          url: '/menu/second/details',
          icon: 'logo-game-controller-b'

        }

      ]

    }

  ];

  constructor(private router: Router , private auth : AuthService) {
    this.router.events.subscribe((event: RouterEvent) => {
      if (event && event.url) {
        this.selectedPath = event.url;
      }
    });
  }


  logOut() {
 this.auth.logOut().then((value) => {
   var currentUser  = this.auth.getCurrentUser();
  console.log('sign out succesfully ' , currentUser);
  this.router.navigateByUrl('/login');
 } , ((error) => {
  console.error(error);
 }));

  }





  ngOnInit() {
  }
}