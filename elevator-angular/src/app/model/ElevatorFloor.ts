export class ElevatorFloor {
  id: number | undefined;
  floor: number | undefined;
  direction: number | undefined;
  floorPickTime: Date | undefined;

  constructor(id?: number, floor?: number, direction?: number, floorPickTime?: Date) {
    this.id = id;
    this.floor = floor;
    this.direction = direction;
    this.floorPickTime = floorPickTime;
  }

}
