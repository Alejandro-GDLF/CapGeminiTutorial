import { Moment } from "moment";
import { Client } from "src/app/client/model/Client";
import { Game } from "src/app/game/model/Game";

export class GameLoan {
    id: number;
    game: Game;
    client: Client;
    loan_date: Moment;
    return_date: Moment;
}