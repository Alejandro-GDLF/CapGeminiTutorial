package com.ccsw.tutorial.gameloan;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.gameloan.specifications.GameLoanSpecDirector;
import com.ccsw.tutorial.gameloan.specifications.GameLoanSpecBuilder;
import com.ccsw.tutorial.gameloan.model.GameLoanSearchDto;
import com.ccsw.tutorial.exception.*;

@Service
@Transactional
public class GameLoanServiceImpl implements GameLoanService {

    @Autowired
    GameLoanRepository gameLoanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    private void checkConstraints(GameLoanDto dto) throws Exception {
        // Return_date > loan_date
        if (dto.getLoan_date().compareTo(dto.getReturn_date()) > 0)
            throw new LoanDateGreaterThanReturnDateException();

        // return_date - loan_date <= 14 days
        if (Duration.between(dto.getLoan_date(), dto.getReturn_date()).toDays() > 14) {
            throw new LoanPeriodGreaterThan14DaysException();
        }
        
        // game can't be lent to no more than two clients at once
        GameLoanSpecBuilder builder = new GameLoanSpecBuilder();
        GameLoanSpecDirector.gameLoansOfGameBetweenDates(builder, dto);
        if (this.gameLoanRepository.count(builder.build()) >= 1) {
            throw new GameLentClientContraintException();
        }
        
        // client can't have no more than 2 games lent
        builder = new GameLoanSpecBuilder();
        GameLoanSpecDirector.gamesLoansOfClientBetweenDates(builder, dto);
        if (this.gameLoanRepository.count(builder.build()) >= 2) {
            throw new ClientHasMaxLentGamesException();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public GameLoan get(Long id) {
        return this.gameLoanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameLoan> findAll() {
        return (List<GameLoan>) this.gameLoanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GameLoan> findPage(GameLoanSearchDto dto) {
    	GameLoanSpecBuilder builder = new GameLoanSpecBuilder();
    	GameLoanSpecDirector.gameLoansInDate(builder, dto.getDate());
    	builder.or(new SearchCriteria("client.id", ":", dto.getClient_id()))
    	.or(new SearchCriteria("game.title", ":", dto.getGame_title()));
    	

        return this.gameLoanRepository.findAll(builder.build(), dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     * @throws Exception 
     */
    @Override
    public void save(Long id, GameLoanDto data) throws Exception {
        this.checkConstraints(data);

        GameLoan gameLoan = id == null ? new GameLoan() : this.get(id);

        BeanUtils.copyProperties(data, gameLoan, "id", "client", "game");

        gameLoan.setClient(clientService.get(data.getClient().getId()));
        gameLoan.setGame(gameService.get(data.getGame().getId()));

        this.gameLoanRepository.save(gameLoan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.gameLoanRepository.deleteById(id);
    }
}
