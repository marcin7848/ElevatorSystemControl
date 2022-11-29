import {Component, OnInit} from '@angular/core';
import {Elevator} from "../../model/Elevator";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {
  elevator: Elevator;
  constructor(){
    this.elevator = new Elevator(5, 2, []);
  }

  ngOnInit() {
    setTimeout(() => {
      this.elevator.id = 98;
    }, 5000);
  }
}
