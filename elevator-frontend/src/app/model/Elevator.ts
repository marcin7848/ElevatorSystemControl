import {ElevatorFloor} from "./ElevatorFloor";

export class Elevator {
  id: number | undefined;
  currentFloor: number | undefined;
  currentStatus: number | undefined;
  selectedFloors: ElevatorFloor[] | undefined;


  constructor(id?: number, currentFloor?: number, currentStatus?: number, selectedFloors?: ElevatorFloor[]) {
    this.id = id;
    this.currentFloor = currentFloor;
    this.currentStatus = currentStatus;
    this.selectedFloors = selectedFloors;
  }
}
