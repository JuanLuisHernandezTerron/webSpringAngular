import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: '',
    children:[
      {path:'register',component: RegisterComponent, pathMatch:'full'},
      {path:"login",component:LoginComponent,pathMatch:"full"}
    ]   
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
