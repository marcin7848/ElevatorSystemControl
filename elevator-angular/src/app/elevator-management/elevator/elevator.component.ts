import {Component, Input, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";

@Component({
  selector: 'app-elevator',
  templateUrl: './elevator.component.html',
  styleUrls: ['./elevator.component.css']
})
export class ElevatorComponent implements OnInit {
  @Input() elevator: Elevator = new Elevator();


  ngOnInit() {
    console.log("on ngOnInit");
  }

  ngOnChanges(){
    console.log("on changes");
  }
}
