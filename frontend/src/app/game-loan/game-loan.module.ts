import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameLoanListComponent } from './game-loan-list/game-loan-list.component';
import { GameLoanEditComponent } from './game-loan-edit/game-loan-edit.component';
import { MatTableModule } from '@angular/material/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';



@NgModule({
  declarations: [
    GameLoanListComponent,
    GameLoanEditComponent,
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule, 
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatSelectModule,
    MatDatepickerModule
  ],
  providers: [
    {
      provide: MAT_DIALOG_DATA,
      useValue: {},
    }
  ]
})
export class GameLoanModule { }
