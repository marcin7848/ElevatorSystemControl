import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Elevator} from "../../model/Elevator";

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

  ngOnInit() {

  }

  ngOnChanges(){
  }

  deleteElevator(){
    this.deleteElevatorEvent.emit(this.elevator);
  }

}
