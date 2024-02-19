package com.ccsw.tutorial.gameloan;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.gameloan.model.GameLoanSearchDto;

public interface GameLoanService {
    /***
     * Recupera un {@link GameLoan} a través de su ID
     * 
     * @param id PK de la entidad
     * @return {@link GameLoan}
     */

    GameLoan get(Long id);

    /**
     * Recupera un listado de préstamos {@link GameLoan}
     *
     * @return {@link List} de {@link GameLoan}
     */
    List<GameLoan> findAll();

    /**
     * Método para recuperar un listado paginado de {@link GameLoan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link GameLoan}
     */
    Page<GameLoan> findPage(GameLoanSearchDto dto);

    /**
     * Método para crear o actualizar un {@link GameLoan}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, GameLoanDto dto);

    /**
     * Método para crear o actualizar un {@link GameLoan}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;
}
