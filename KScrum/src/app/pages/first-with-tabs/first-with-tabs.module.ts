import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
 
import { IonicModule } from '@ionic/angular';
 
import { FirstWithTabsPage } from './first-with-tabs.page';
 
const routes: Routes = [
 
  {
    path: 'tabs',
    component: FirstWithTabsPage,
    children: [
      {
        path: 'projects',
        loadChildren: '../projects/projects.module#ProjectsPageModule'
      }  ,
      {
        path: 'projects/details',
        loadChildren: '../details/details.module#DetailsPageModule'
      },
      {
        path: 'tab2',
        loadChildren: '../tab2/tab2.module#Tab2PageModule'
      }
    ]
  }, { 
    path: '',
    redirectTo:'tabs/projects',
    pathMatch:'full'
    
  }
];
 
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [FirstWithTabsPage]
})
export class FirstWithTabsPageModule {}