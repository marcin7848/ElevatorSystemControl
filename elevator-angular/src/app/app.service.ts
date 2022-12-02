import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {SnackBarComponent} from "./snack-bar/snack-bar.component";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private snackBar: MatSnackBar) { }
  openSnackBar(text: string) {
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 3 * 1000,
      panelClass: ['snackbar'],
      verticalPosition: "top",
      horizontalPosition: "center",
      data: text
    });
  }

  openSnackBarError(httpError: HttpErrorResponse) {
    let message = "";
    if(httpError.error.errors.length > 0){
      message = httpError.error.errors.reduce((prevErr:string, err:string)=> {
        return prevErr + ' | ' + err;
      });
    }
    else{
      message = httpError.error.message;
    }

    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 3 * 1000,
      panelClass: ['snackbar-error'],
      verticalPosition: "top",
      horizontalPosition: "center",
      data: message
    });
  }
}
