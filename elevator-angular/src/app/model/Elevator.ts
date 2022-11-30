import {ElevatorFloor} from "./ElevatorFloor";

export class Elevator {
  id: number | undefined;
  currentFloor: number | undefined;
  currentStatus: number | undefined;
  targetFloors: ElevatorFloor[] | undefined;


  constructor(id?: number, currentFloor?: number, currentStatus?: number, targetFloors?: ElevatorFloor[]) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.currentStatus = currentStatus;
    this.targetFloors = targetFloors;
  }
}
