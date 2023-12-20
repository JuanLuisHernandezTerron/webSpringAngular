import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: "",
    loadChildren: ()=> import('./pages/main-web/main-web.module').then((m)=>m.MainWebModule),
    pathMatch:'full'
  },
  {
    path:"auth",
    loadChildren: ()=> import('./pages/auth/auth.module').then((m)=>m.AuthModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
