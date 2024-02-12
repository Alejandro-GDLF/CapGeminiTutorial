import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ClientService } from 'src/app/client/client.service';
import { GameService } from 'src/app/game/game.service';
import { GameLoanService } from '../game-loan.service';
import { Game } from 'src/app/game/model/Game';
import { Client } from 'src/app/client/model/Client';
import { GameLoan } from '../model/GameLoan';

@Component({
  selector: 'app-game-loan-edit',
  templateUrl: './game-loan-edit.component.html',
  styleUrls: ['./game-loan-edit.component.scss']
})
export class GameLoanEditComponent implements OnInit {
    games: Game[] = [];
    clients: Client[] = [];
    gameLoan: GameLoan;
    ONE_DAY = 24 * 3600 * 1000;

    constructor(
        public dialogRef: MatDialogRef<GameLoanEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private gameLoanService: GameLoanService,
    ) { }

    checkContraints(): boolean {
        if( this.gameLoan.loan_date >= this.gameLoan.return_date ) {
            alert("La fecha de inicio es mayor que la de final");
            return false;
        }

        const diffDays = (this.gameLoan.return_date.getTime()
        - this.gameLoan.loan_date.getTime())/ this.ONE_DAY;

        if( diffDays > 14 ) {
            alert("La duración del préstamo no puede ser mayor a la de 14 días");
            return false;
        }

        return true;
    }

    ngOnInit(): void {
        this.clients = this.data.clients;
        this.games = this.data.games;
        this.gameLoan = new GameLoan();
    }

    onSave(): void {
        if(this.checkContraints())
            this.gameLoanService.saveGameLoan(this.gameLoan).subscribe({
                next: data => {
                    this.dialogRef.close();
                },
                error: err => {
                    alert(err.error);
                }
        });
    }

    onClose(): void {
        this.dialogRef.close();
    }
}
