import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainUserLogginComponent } from './main-user-loggin/main-user-loggin.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';
const routes: Routes = [
  {
    path: '',
    component: MainUserLogginComponent, 
    pathMatch:'full',
    children:[]   
  },
  {
    path:'profileUser',
    component: ProfileUserComponent, 
    pathMatch:'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class userlogginrouting { }
