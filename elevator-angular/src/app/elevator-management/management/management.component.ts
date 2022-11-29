import {Component, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";
import {ElevatorFloor} from "../../model/ElevatorFloor";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {
  elevators: Elevator[];

  constructor(){
    this.elevators = [
      new Elevator(1, 2, [new ElevatorFloor(1, 5), new ElevatorFloor(1, 8)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),

      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 222222222222222, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
      new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)]),
        new Elevator(5, 0, [new ElevatorFloor(5, 1), new ElevatorFloor(5, 2)])
    ];
  }

  ngOnInit() {
  }
}
