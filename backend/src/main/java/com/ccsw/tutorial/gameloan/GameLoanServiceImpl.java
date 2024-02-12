package com.ccsw.tutorial.gameloan;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.gameloan.model.GameLoanFiltersDto;
import com.ccsw.tutorial.gameloan.model.GameLoanSearchDto;

@Service
@Transactional
public class GameLoanServiceImpl implements GameLoanService {

    @Autowired
    GameLoanRepository gameLoanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    private void checkConstraints(GameLoanDto dto) throws DataIntegrityViolationException {
        // Return_date > loan_date
        if (dto.getLoan_date().compareTo(dto.getReturn_date()) > 0)
            throw new DataIntegrityViolationException("Loan date can't be greater than Return date");

        // return_date - loan_date <= 14 days
        if (Duration.between(dto.getLoan_date(), dto.getReturn_date()).toDays() > 14) {
            throw new DataIntegrityViolationException("Loan duration can't be more than 14 days");
        }
        // game can't be lent to no more than two clients at once
        if (this.gameLoanRepository.count(GameLoanFilterSpecs.gameLoansOfGameBetweenDates(dto)) >= 1) {
            throw new DataIntegrityViolationException("Game can not be lent to two clients at once.");
        }
        // client can't have no more than 2 games lent
        if (this.gameLoanRepository.count(GameLoanFilterSpecs.gamesLoansOfClientBetweenDates(dto)) >= 2) {
            throw new DataIntegrityViolationException("Clients can not have two games lent at once.");
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
    public Page<GameLoan> findPage(GameLoanSearchDto dto, GameLoanFiltersDto filters_dto) {
        Specification<GameLoan> gameTitleSpec = GameLoanFilterSpecs.hasGameTitle(filters_dto.getGame_title());
        
        GameLoanSpecification clientSpec = new GameLoanSpecification(
                new SearchCriteria("client.id", ":", filters_dto.getClient_id()));
        Specification<GameLoan> timeSpec = GameLoanFilterSpecs.gameLoansInDate(filters_dto.getDate());

        Specification<GameLoan> spec = Specification.where(timeSpec).or(clientSpec).or(gameTitleSpec);

        return this.gameLoanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameLoanDto data) {
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
