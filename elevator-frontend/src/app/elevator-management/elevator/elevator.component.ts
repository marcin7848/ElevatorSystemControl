import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Elevator} from "../../model/Elevator";
import {ElevatorService} from "../elevator.service";
import {AppService} from "../../app.service";
import {ElevatorFloor} from "../../model/ElevatorFloor";

@Component({
  selector: 'app-elevator',
  templateUrl: './elevator.component.html',
  styleUrls: ['./elevator.component.css']
})
export class ElevatorComponent implements OnInit {

  readonly MIN_FLOOR: number = -2;
  readonly MAX_FLOOR: number = 6;

  @Input() elevator: Elevator = new Elevator();
  readonly range:number[] = Array.from({length: (1 + this.MAX_FLOOR - this.MIN_FLOOR)},
    (v, k) => k + this.MIN_FLOOR);

  @Output() deleteElevatorEvent = new EventEmitter<Elevator>();

  constructor(private elevatorService: ElevatorService, private appService: AppService){
  }
  ngOnInit() {

  }

  ngOnChanges(){
  }

  deleteElevator(){
    this.deleteElevatorEvent.emit(this.elevator);
  }

  addNewElevatorFloor(floor: number, direction: number){
    this.elevatorService.addNewElevatorFloor(new ElevatorFloor(0, floor, direction), this.elevator.id!)
      .subscribe({
        next: (elevatorFloor) => {
          this.elevator.selectedFloors!.push(elevatorFloor);
        },
        error: msg => {
          this.appService.openSnackBarError(msg);
        }
      });
  }

}
