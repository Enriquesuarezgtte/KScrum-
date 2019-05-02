import { LoginPage } from './../login/login.page';
import { Component, OnInit } from '@angular/core';
import { Router, RouterEvent } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { IUser } from '../../models/User.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {
  selectedPath = '';

  public currentUser: IUser;
  pages = [
    {
      title: 'Projects',
      icon: 'folder-open',
      url: '/menu/first/tabs/projects'
    }
    , {
      title: 'Manage Projects',
      children: [
        {
          title: 'Create Project',
          url: '/menu/projectEdition',
          icon: 'create'
        }

      ]

    }

  ];

  constructor(private router: Router , private auth : AuthService) {
    this.router.events.subscribe((event: RouterEvent) => {
      if (event && event.url) {
        this.selectedPath = event.url;
        console.log("on router", this.selectedPath );
      }
    });
  }



  getUserData() {
    this.currentUser = this.auth.getCurrentUser();
    console.log(this.currentUser);
  }

  logOut() {
    this.auth.logOut().then(() => {
      this.currentUser = this.auth.getCurrentUser();
      console.log('sign out succesfully ', this.currentUser);
      this.router.navigateByUrl('/login');
    }, ((error) => {
      console.error(error);
    }));

  }





  ngOnInit() {
    this.getUserData();
  }


  }
