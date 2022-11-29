export class ElevatorFloor {
  id: number | undefined;
  floor: number | undefined;
  floorPickTime: Date | undefined;

  constructor(id?: number, floor?: number, floorPickTime?: Date) {
    this.id = id;
    this.floor = floor;
    this.floorPickTime = floorPickTime;
  }

}
