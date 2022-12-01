import {Component, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";
import {ElevatorFloor} from "../../model/ElevatorFloor";
import {ElevatorService} from "../elevator.service";
import {AppService} from "../../app.service";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {
  readonly MAX_ELEVATORS: number = 16;
  elevators: Elevator[] = [];
  addNewElevatorDisabled: boolean = true;

  constructor(private elevatorService: ElevatorService, private appService: AppService){
  }

  ngOnInit() {
    this.elevatorService.getAllElevators()
      .subscribe({
        next: (elevators) => {
          this.elevators = elevators;
          this.addNewElevatorDisabled = this.elevators.length >= this.MAX_ELEVATORS;
        },
        error: msg => {
          this.appService.openSnackBarError(msg);
        }
      });

  }

  addNewElevator(){
    this.addNewElevatorDisabled = true;
    this.elevatorService.addNewElevator()
      .subscribe({
        next: (elevator) => {
          this.elevators.push(elevator);
          this.appService.openSnackBar("A new elevator has been added!");
          this.addNewElevatorDisabled = false;
        },
        error: msg => {
          this.appService.openSnackBarError(msg);
          this.addNewElevatorDisabled = false;
        }
      });
  }



}
