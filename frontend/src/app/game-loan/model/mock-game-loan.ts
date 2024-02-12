import { GameLoanPage } from "./GameLoanPage";

export const GAME_LOAN_DATA: GameLoanPage = {
    content: [
        {
            id: 1, 
            game: 
                { id: 1, title: 'Juego 1', age: 6, category: { id: 1, name: 'Categoría 1' }, author: { id: 1, name: 'Autor 1', nationality: 'Nacionalidad 1' } }, 
            client:  
                {id: 1, name: "Cliente 1"},
            loan_date: new Date(),
            return_date: new Date()
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