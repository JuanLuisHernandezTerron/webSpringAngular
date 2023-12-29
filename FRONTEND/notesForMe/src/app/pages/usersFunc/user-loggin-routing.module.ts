import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainUserLogginComponent } from './main-user-loggin/main-user-loggin.component';

const routes: Routes = [
  {
    path: '',
    component: MainUserLogginComponent, pathMatch:'full',
    children:[]   
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class userlogginrouting { }
