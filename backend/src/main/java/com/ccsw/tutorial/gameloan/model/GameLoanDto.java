package com.ccsw.tutorial.gameloan.model;

import java.time.LocalDateTime;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class GameLoanDto {
    private Long id;

    private GameDto game;

    private ClientDto client;

    private LocalDateTime loan_date;

    private LocalDateTime return_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public LocalDateTime getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(LocalDateTime loan_date) {
        this.loan_date = loan_date;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }

}
