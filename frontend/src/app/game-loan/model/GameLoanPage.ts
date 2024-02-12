import { Pageable } from "src/app/core/model/page/Pageable";
import { GameLoan } from "./GameLoan";

export class GameLoanPage {
    content: GameLoan[];
    pageable: Pageable;
    totalElements: number;
}