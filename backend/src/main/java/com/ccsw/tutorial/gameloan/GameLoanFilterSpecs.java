package com.ccsw.tutorial.gameloan;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.game.model.*;

public class GameLoanFilterSpecs {
    public static Specification<GameLoan> gameLoansBetweenDates(LocalDateTime startTime, LocalDateTime endTime) {
        GameLoanSpecification startTimeSpec = new GameLoanSpecification(
                new SearchCriteria("loan_date", "<=", endTime));
        GameLoanSpecification endTimeSpec = new GameLoanSpecification(
                new SearchCriteria("return_date", ">=", startTime));

        return Specification.where(startTimeSpec).and(endTimeSpec);
    }

    public static Specification<GameLoan> gameLoansOfGameBetweenDates(GameLoanDto dto) {
        Specification<GameLoan> time_spec = GameLoanFilterSpecs.gameLoansBetweenDates(dto.getLoan_date(), dto.getReturn_date());
        GameLoanSpecification game_id = new GameLoanSpecification(
                new SearchCriteria("game.id", ":", dto.getGame().getId()));

        return Specification.where(time_spec).and(game_id);
    }

    public static Specification<GameLoan> gamesLoansOfClientBetweenDates(GameLoanDto dto) {
        Specification<GameLoan> time_spec = GameLoanFilterSpecs.gameLoansBetweenDates(dto.getLoan_date(), dto.getReturn_date());
        GameLoanSpecification client_id = new GameLoanSpecification(
                new SearchCriteria("client.id", ":", dto.getClient().getId()));

        return Specification.where(time_spec).and(client_id);
    }
    
    public static Specification<GameLoan> hasGameTitle(String title) {
    	return (root, query, builder) -> {
    		if(title == null) return null;
    		Join<GameLoan, Game> gameJoin = root.join("game", JoinType.INNER);
    		return builder.equal(builder.lower(gameJoin.get("title")), title.trim().toLowerCase());
    	};
    }
    
    public static Specification<GameLoan> gameLoansInDate(LocalDateTime date) {
    	return gameLoansBetweenDates(date, date);
    }
}
