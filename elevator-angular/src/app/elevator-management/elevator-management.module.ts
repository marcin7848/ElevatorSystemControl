import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagementComponent } from './management/management.component';
import { ElevatorComponent } from './elevator/elevator.component';



@NgModule({
  declarations: [
    ManagementComponent,
    ElevatorComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ElevatorManagementModule { }
