package com.ccsw.tutorial.gameloan.specifications;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class GameLoanSpecDirector {
	public static Specification<GameLoan> gameLoansBetweenDates(LocalDateTime startTime, LocalDateTime endTime) {
		GameLoanSpecBuilder builder = new GameLoanSpecBuilder(new SearchCriteria("loan_date", "<=", endTime))
				.and(new SearchCriteria("return_date", ">=", startTime));

        return builder.build();
    }
	
	public static Specification<GameLoan> gameLoansOfGameBetweenDates(GameLoanDto dto) {
		GameLoanSpecBuilder builder = new GameLoanSpecBuilder(gameLoansBetweenDates(dto.getLoan_date(), dto.getReturn_date()))
				.and(new SearchCriteria("game.id", ":", dto.getGame().getId()));

        return builder.build();
    }
	
	public static Specification<GameLoan> gamesLoansOfClientBetweenDates(GameLoanDto dto) {
		GameLoanSpecBuilder builder = new GameLoanSpecBuilder(gameLoansBetweenDates(dto.getLoan_date(), dto.getReturn_date()))
				.and(new SearchCriteria("client.id", ":", dto.getClient().getId()));

        return builder.build();
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
