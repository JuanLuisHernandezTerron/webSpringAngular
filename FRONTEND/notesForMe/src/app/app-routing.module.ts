import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardUserGuard } from './guards/auth-guard-user.guard';
import { GuardUserLoginGuard } from './guards/guard-user-login.guard';

const routes: Routes = [
  {
    path: "",
    loadChildren: ()=> import('./pages/main-web/main-web.module').then((m)=>m.MainWebModule),
    pathMatch:'full',
  },
  {
    path:"auth",
    loadChildren: ()=> import('./pages/auth/auth.module').then((m)=>m.AuthModule),
    canActivate:[GuardUserLoginGuard]
  },
  {
    path:"mainUserLoggin",
    loadChildren:()=>import('./pages/usersFunc/user-loggin.module').then((m)=>m.UserLogginModule),
    canActivate: [AuthGuardUserGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
