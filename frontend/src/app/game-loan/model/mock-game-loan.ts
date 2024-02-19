import { Moment } from "moment";
import { GameLoanPage } from "./GameLoanPage";
import moment from "moment";

export const GAME_LOAN_DATA: GameLoanPage = {
    content: [
        {
            id: 1, 
            game: 
                { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, 
            client:  
                {id: 1, name: "Cliente 1"},
            loan_date: moment(),
            return_date: moment()
        }
    ],
    pageable : {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 7
}