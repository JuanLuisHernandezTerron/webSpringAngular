import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainWebComponent } from './main/main-web.component';
const routes: Routes = [
  {
    path: "",
    component: MainWebComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainWebRoutingModule { }
