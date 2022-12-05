import {Component, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";
import {ElevatorService} from "../elevator.service";
import {AppService} from "../../app.service";
import {interval, timer} from "rxjs";

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
    this.reloadElevatorsDataInInterval();
  }

  ngOnInit() {
    this.elevatorService.getAllElevators()
      .subscribe({
        next: (elevators) => {
          this.elevators = elevators;
          this.disableElevatorButton();
        },
        error: msg => {
          this.appService.openSnackBarError(msg);
        }
      });
  }

  disableElevatorButton(){
    this.addNewElevatorDisabled = this.elevators.length >= this.MAX_ELEVATORS;
  }

  addNewElevator(){
    this.addNewElevatorDisabled = true;
    this.elevatorService.addNewElevator()
      .subscribe({
        next: (elevator) => {
          elevator.selectedFloors = [];
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

  deleteElevator($elevatorEvent: Elevator){
    this.elevatorService.deleteElevator($elevatorEvent)
      .subscribe({
        next: () => {
          this.appService.openSnackBar("The elevator has been deleted!");
          this.elevators.forEach((e, index) => {
            if(e.id == $elevatorEvent.id) this.elevators.splice(index, 1);
          });
          this.disableElevatorButton();
        },
        error: msg => {
          this.appService.openSnackBarError(msg);
        }
      });
  }

  reloadElevatorsDataInInterval(){
    interval(1000).subscribe((x =>{
      this.elevatorService.getAllElevators()
        .subscribe({
          next: (elevators) => {
            this.updateElevators(elevators);
          },
          error: msg => {
            this.appService.openSnackBarError(msg);
          }
        });
    }));
  }

  updateElevators(elevators: Elevator[]){
    this.elevators.forEach(elevator => {
      const index = elevators.findIndex(e => e.id == elevator.id);
      if(index == -1){
        this.ngOnInit();
        return;
      }

      elevator.currentFloor = elevators[index].currentFloor;
      elevator.currentStatus = elevators[index].currentStatus;
      elevator.selectedFloors = elevators[index].selectedFloors;
    });
  }


}
