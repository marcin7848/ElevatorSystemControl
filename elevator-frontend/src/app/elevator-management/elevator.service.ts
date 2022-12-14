import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Elevator} from "../model/Elevator";
import {ElevatorFloor} from "../model/ElevatorFloor";

@Injectable({
  providedIn: 'root'
})
export class ElevatorService {

  mainHttp: string = "/elevator/";
  constructor(private http: HttpClient) { }

  getAllElevators(): Observable<Elevator[]> {
    return this.http.get<Elevator[]>(this.mainHttp + 'getAll');
  }

  addNewElevator(): Observable<Elevator> {
    return this.http.post<Elevator>(this.mainHttp + 'add', {});
  }

  deleteElevator(elevator: Elevator) {
    return this.http.delete(this.mainHttp + elevator.id + '/delete');
  }

  addNewElevatorFloor(elevatorFloor: ElevatorFloor, elevatorId: number): Observable<ElevatorFloor> {
    return this.http.post<ElevatorFloor>(this.mainHttp + elevatorId + '/elevatorFloor/add', elevatorFloor);
  }

}
