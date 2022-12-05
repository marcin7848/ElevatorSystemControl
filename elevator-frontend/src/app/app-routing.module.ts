import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ManagementComponent} from "./elevator-management/management/management.component";

const routes: Routes = [
  { path: '', component: ManagementComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
