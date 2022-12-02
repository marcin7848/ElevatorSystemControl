import { NgModule } from '@angular/core';
import { ManagementComponent } from './management/management.component';
import { ElevatorComponent } from './elevator/elevator.component';
import {MatButtonModule} from '@angular/material/button';
import {CommonModule} from "@angular/common";
import {MatCardModule} from "@angular/material/card";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatIconModule} from "@angular/material/icon";

@NgModule({
  declarations: [
    ManagementComponent,
    ElevatorComponent
  ],
    imports: [
        CommonModule,
        MatButtonModule,
        MatCardModule,
        MatTooltipModule,
        MatIconModule
    ]
})
export class ElevatorManagementModule { }
