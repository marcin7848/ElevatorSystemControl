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
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: 3 * 1000,
      panelClass: ['snackbar-error'],
      verticalPosition: "top",
      horizontalPosition: "center",
      data: httpError.error.message
    });
  }
}
