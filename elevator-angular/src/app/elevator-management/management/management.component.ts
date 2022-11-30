import {Component, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";
import {ElevatorFloor} from "../../model/ElevatorFloor";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {
  readonly MAX_ELEVATORS: number = 16;
  elevators: Elevator[];
  addNewElevatorDisabled: boolean = true;

  constructor(){
    this.elevators = [
      new Elevator(1, 2, 0, [new ElevatorFloor(1, 5), new ElevatorFloor(1, 8)]),
      new Elevator(5, 0, 3, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),

      new Elevator(5, 0, 2, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 5, 3, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 1, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 3, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 2, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 3, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 1, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, 2, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
        new Elevator(5, 0, 3, []),
          new Elevator(5, 0, 3, []),
      new Elevator(5, 0, 3, []),
      new Elevator(5, 0, 3, [])
    ];
  }

  ngOnInit() {
    this.addNewElevatorDisabled = this.elevators.length >= this.MAX_ELEVATORS;
  }


}
