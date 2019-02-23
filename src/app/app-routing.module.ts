import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomePageModule'
  },
  {
    path: 'list',
    loadChildren: './list/list.module#ListPageModule'
  },
  { path: 'projects',
   loadChildren: './projects/projects.module#ProjectsPageModule' },
   
  { path: 'sproject',
   loadChildren: './sproject/sproject.module#SProjectPageModule' },

  { path: 'login',
   loadChildren: './login/login.module#LoginPageModule' },


  { path: 'userprofile', 
  loadChildren: './user-profile/user-profile.module#UserProfilePageModule' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
