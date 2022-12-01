import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Elevator} from "../model/Elevator";

@Injectable({
  providedIn: 'root'
})
export class ElevatorService {

  mainHttp: string = "/elevator/";
  constructor(private http: HttpClient) { }

  addNewElevator(): Observable<Elevator> {
    return this.http.post<Elevator>(this.mainHttp + 'add', {});
  }


}
