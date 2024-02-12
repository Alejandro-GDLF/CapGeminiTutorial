package com.ccsw.tutorial.gameloans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.gameloan.GameLoanRepository;
import com.ccsw.tutorial.gameloan.model.*;
import com.ccsw.tutorial.gameloan.*;

@ExtendWith(MockitoExtension.class)
public class GameLoansTest {
    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;
    
    @Mock
    private GameLoanRepository gameLoanRepository;
    
    @InjectMocks
    private GameLoanServiceImpl gameLoanService;
    
    private static final Long EXISTS_GAMELOAN_ID = 1L;
    private static final Long NOT_EXISTS_GAMELOAN_ID = 0L;

    @Test
    public void getExistentLoanIdShouldReturnLoan() {
    	GameLoan gameLoan = mock(GameLoan.class);
    	
    	when(gameLoan.getId()).thenReturn(EXISTS_GAMELOAN_ID);
    	when(gameLoanRepository.findById(EXISTS_GAMELOAN_ID)).thenReturn(Optional.of(gameLoan));
    	GameLoan gameLoanResponse = gameLoanService.get(EXISTS_GAMELOAN_ID);
    	
    	assertNotNull(gameLoanResponse);
    	assertEquals(EXISTS_GAMELOAN_ID, gameLoanResponse.getId());
    }

    @Test
    public void getNotExistentLoanIdShouldReturnNull() {
    	when(gameLoanRepository.findById(NOT_EXISTS_GAMELOAN_ID)).thenReturn(Optional.empty());
    	
    	GameLoan gameLoan = gameLoanService.get(NOT_EXISTS_GAMELOAN_ID);
    	
    	assertNull(gameLoan);
    }
}
