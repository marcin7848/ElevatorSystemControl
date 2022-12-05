export class ElevatorFloor {
  id: number | undefined;
  floor: number | undefined;
  direction: number | undefined;
  position: number | undefined;
  floorPickTime: Date | undefined;

  constructor(id?: number, floor?: number, direction?: number, position?: number, floorPickTime?: Date) {
    this.id = id;
    this.floor = floor;
    this.direction = direction;
    this.position = position;
    this.floorPickTime = floorPickTime;
  }

}
