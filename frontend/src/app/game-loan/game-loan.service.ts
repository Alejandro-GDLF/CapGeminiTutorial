import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { GameLoan } from './model/GameLoan';
import { GameLoanPage } from './model/GameLoanPage';
import { GAME_LOAN_DATA } from './model/mock-game-loan';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GameLoanFilters } from './model/GameLoanFilters';

@Injectable({
  providedIn: 'root'
})
export class GameLoanService {

    constructor(
        private http: HttpClient
    ) { }

    getGameLoans(pageable: Pageable, filters?: GameLoanFilters): Observable<GameLoanPage> {
        let payload = {
            pageable: pageable
        };

        if( filters )
            Object.assign(payload, {
                client_id: filters.client_id,
                game_title: filters.game_title,
                date: filters.date
            });

        return this.http.post<GameLoanPage>('http://localhost:8080/gameloan', payload);
    }

    saveGameLoan(game_loan: GameLoan): Observable<void> {
        let url = 'http://localhost:8080/gameloan';

        if(game_loan.id != null ) url += '/' + game_loan.id;

        return this.http.put<void>(url, game_loan);
    }

    deleteGameLoan(idGameLoan: number): Observable<void> {
        return this.http.delete<void>('http://localhost:8080/gameloan/' + idGameLoan);
    }
}
