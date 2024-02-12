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
	public static void gameLoansBetweenDates(GameLoanSpecBuilder builder, LocalDateTime startTime, LocalDateTime endTime) {
		builder.and( new SearchCriteria("loan_date", "<=", endTime))
				.and(new SearchCriteria("return_date", ">=", startTime));
    }
	
	public static void gameLoansOfGameBetweenDates(GameLoanSpecBuilder builder, GameLoanDto dto) {
		gameLoansBetweenDates(builder, dto.getLoan_date(), dto.getReturn_date());
		builder.and(new SearchCriteria("game.id", ":", dto.getGame().getId()));
    }
	
	public static void gamesLoansOfClientBetweenDates(GameLoanSpecBuilder builder, GameLoanDto dto) {
			gameLoansBetweenDates(builder, dto.getLoan_date(), dto.getReturn_date());
			builder.and(new SearchCriteria("client.id", ":", dto.getClient().getId()));
    }
	
	public static void gameLoansInDate(GameLoanSpecBuilder builder, LocalDateTime date) {
    	gameLoansBetweenDates(builder, date, date);
    }
}
