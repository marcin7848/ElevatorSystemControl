import {ElevatorFloor} from "./ElevatorFloor";

export class Elevator {
  id: number | undefined;
  currentFloor: number | undefined;
  targetFloors: ElevatorFloor[] | undefined;


  constructor(id?: number, currentFloor?: number, targetFloors?: ElevatorFloor[]) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.targetFloors = targetFloors;
  }
}
