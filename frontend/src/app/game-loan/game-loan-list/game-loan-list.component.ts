import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { GameLoan } from '../model/GameLoan';
import { GameLoanService } from '../game-loan.service';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { GameLoanEditComponent } from '../game-loan-edit/game-loan-edit.component';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Game } from 'src/app/game/model/Game';
import { Client } from 'src/app/client/model/Client';
import { ClientService } from 'src/app/client/client.service';
import { GameService } from 'src/app/game/game.service';
import { GameLoanFilters } from '../model/GameLoanFilters';

@Component({
  selector: 'app-game-loan-list',
  templateUrl: './game-loan-list.component.html',
  styleUrls: ['./game-loan-list.component.scss']
})
export class GameLoanListComponent implements OnInit {
    pageNumber: number = 0;
    pageSize: number = 5;
    totalElements: number = 0;
    gameLoanFilters: GameLoanFilters;
    games: Game[] = [];
    clients: Client[] = [];

    dataSource = new MatTableDataSource<GameLoan>();
    displayedColumns: string[] = ['id', 'game_name', 'client_name', 'loan_date', 'return_date', 'action'];

    constructor(
        private gameLoanService: GameLoanService,
        public dialog: MatDialog,
        private clientService: ClientService,
        private gameService: GameService,
    ) {
        this.gameLoanFilters = new GameLoanFilters();
     }

    ngOnInit(): void {
        this.loadPage();
        this.gameService.getGames().subscribe(result => this.games = result);
        this.clientService.getClients().subscribe(result => this.clients = result);
    }

    formatDate(date: Date): string {
        return date.toLocaleDateString('es-ES');
    }

    onCleanFilter(): void {
        this.gameLoanFilters = new GameLoanFilters();

        this.onSearch();
    }

    onSearch(): void {
        this.gameLoanService.getGameLoans(this.getPage(), this.gameLoanFilters)
            .subscribe(data => this.loadData(data));
    }

    getPage(): Pageable {
        let pageable: Pageable = {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            sort: [{
                property: 'id',
                direction: 'ASC'
            }]
        }

        return pageable;
    }

    loadPage(event?: PageEvent) {
        let pageable = this.getPage();

        if(event != null ) {
            pageable.pageSize = event.pageSize;
            pageable.pageNumber = event.pageIndex;
        }

        this.gameLoanService.getGameLoans(pageable).subscribe(data => {
            this.loadData(data);
        });
    }

    loadData(data: any) {
        data.content.forEach(game_loan => {
            game_loan.loan_date = new Date(game_loan.loan_date);
            game_loan.return_date = new Date(game_loan.return_date);
        });
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
        this.dataSource.data = data.content;
    }

    createGameLoan() {
        const dialogRef = this.dialog.open(GameLoanEditComponent, {
            data: {
                clients: this.clients,
                games: this.games
            }
        });

        dialogRef.afterClosed().subscribe(result => this.ngOnInit());
    }

    deleteGameLoan(gameLoan: GameLoan) {
        const dialogRef = this.dialog.open(DialogConfirmationComponent,{
            data: {title: "Eliminar préstamo", description: "Atención si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?"}
        });

        dialogRef.afterClosed().subscribe(result => {
            if(!result) return;

            this.gameLoanService.deleteGameLoan(gameLoan.id).subscribe(result => this.ngOnInit());
        });
    }
}
