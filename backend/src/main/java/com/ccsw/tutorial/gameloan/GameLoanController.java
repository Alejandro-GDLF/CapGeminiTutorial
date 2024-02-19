package com.ccsw.tutorial.gameloan;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.gameloan.model.GameLoan;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.gameloan.model.GameLoanSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Game Loan", description = "API of Game Loan")
@RequestMapping(value = "/gameloan")
@RestController
@CrossOrigin(origins = "*")
public class GameLoanController {
    @Autowired
    GameLoanService gameLoanService;

    @Autowired
    ModelMapper mapper;

    @Operation(summary = "Find", description = "Method that return a list of all GameLoans")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<GameLoanDto> findAll() {
        List<GameLoan> gameLoans = this.gameLoanService.findAll();

        return gameLoans.stream().map(e -> mapper.map(e, GameLoanDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para recuperar un listado paginado de {@link GameLoan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link GameLoanDto}
     */
    @Operation(summary = "Find Page", description = "Method that return a page of GameLoans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<GameLoanDto> findPage(@RequestBody GameLoanSearchDto search_dto) {
        Page<GameLoan> page = this.gameLoanService.findPage(search_dto);

        return new PageImpl<>(
                page.getContent().stream().map(e -> mapper.map(e, GameLoanDto.class)).collect(Collectors.toList()),
                page.getPageable(), page.getTotalElements());
    }

    /**
     * Método para crear o actualizar un {@link GameLoan}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     * @throws Exception 
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a GameLoan")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody GameLoanDto dto) throws Exception {

        this.gameLoanService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link GameLoan}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a GameLoan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.gameLoanService.delete(id);
    }
}
