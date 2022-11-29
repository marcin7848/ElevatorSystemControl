import {ElevatorFloor} from "./ElevatorFloor";

export class Elevator {
  id: number;
  currentFloor: number;
  nextFloors: ElevatorFloor[] | undefined;


  constructor(id: number, currentFloor: number, nextFloors?: ElevatorFloor[]) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.nextFloors = nextFloors;
  }
}
