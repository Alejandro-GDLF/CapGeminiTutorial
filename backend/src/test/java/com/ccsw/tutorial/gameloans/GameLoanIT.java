package com.ccsw.tutorial.gameloans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.gameloan.model.GameLoanDto;
import com.ccsw.tutorial.gameloan.model.GameLoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameLoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/gameloan";
    
    private static final int TOTAL_GAMELOANS = 9;
    private static final int PAGE_SIZE = 5;

    public static final Long DELETE_GAMELOAN_ID = Long.valueOf(TOTAL_GAMELOANS);
    public static final Long MODIFY_GAMELOAN_ID = 3L;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<GameLoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<GameLoanDto>>() {
    };
    
    ParameterizedTypeReference<List<GameLoanDto>> responseTypeList = new ParameterizedTypeReference<List<GameLoanDto>>() {
    };
    
    @Test
    public void findAllShouldReturnListOfAllGameLoans() {
    	ResponseEntity<List<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseTypeList);
    	
    	assertNotNull(response);
    	assertEquals(TOTAL_GAMELOANS, response.getBody().size());
    }

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
        GameLoanSearchDto gameLoanSearchDto = new GameLoanSearchDto();
        gameLoanSearchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(gameLoanSearchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_GAMELOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        GameLoanSearchDto searchDto = new GameLoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_GAMELOANS, response.getBody().getTotalElements());

        int elementsCount = TOTAL_GAMELOANS - PAGE_SIZE;
        assertEquals(elementsCount, response.getBody().getContent().size());
    }
    

    @Test
    public void saveWithoutIdShouldCreateNewGameLoan() {
        long newGameLoanId = TOTAL_GAMELOANS + 1;
        long newGameLoanSize = TOTAL_GAMELOANS + 1;
        Long NEW_GAMELOAN_CLIENT_ID = 1L;

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_GAMELOAN_CLIENT_ID);

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);

        GameLoanDto dto = new GameLoanDto();
        dto.setClient(clientDto);
        dto.setGame(gameDto);
        dto.setLoan_date(LocalDateTime.of(2024, 2, 2, 0, 0));
        dto.setReturn_date(LocalDateTime.of(2024, 2, 5, 0, 0));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        GameLoanSearchDto searchDto = new GameLoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newGameLoanId));

        ResponseEntity<List<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, new HttpEntity<>(searchDto), responseTypeList);

        assertNotNull(response);
        assertEquals(newGameLoanSize, response.getBody().size());

        GameLoanDto gameLoan = response.getBody().stream()
                .filter(item -> item.getId().equals(newGameLoanId)).findFirst().orElse(null);
        
        assertNotNull(gameLoan);
        assertEquals(gameLoan.getClient().getId(), NEW_GAMELOAN_CLIENT_ID);
    }

    @Test
    public void saveWithExistIdShouldModifyGameLoan() {
        Long NEW_GAMELOAN_CLIENT_ID = 1L;

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_GAMELOAN_CLIENT_ID);

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);

        GameLoanDto dto = new GameLoanDto();
        dto.setClient(clientDto);
        dto.setGame(gameDto);
        dto.setLoan_date(LocalDateTime.of(2024, 2, 2, 0, 0));
        dto.setLoan_date(LocalDateTime.of(2024, 2, 5, 0, 0));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_GAMELOAN_ID, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);
        
        GameLoanSearchDto searchDto = new GameLoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<List<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseTypeList);

        assertNotNull(response);
        assertEquals(TOTAL_GAMELOANS, response.getBody().size());

        GameLoanDto game = response.getBody().stream()
                .filter(item -> item.getId().equals(MODIFY_GAMELOAN_ID)).findFirst().orElse(null);
        assertNotNull(game);
        assertEquals(NEW_GAMELOAN_CLIENT_ID, game.getClient().getId());
    }

    @Test
    public void saveWithNotExistIdShouldThrowException() {
        long gameLoanId = TOTAL_GAMELOANS + 1;

        GameLoanDto dto = new GameLoanDto();

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + gameLoanId,
                HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteGameLoan() {
        long newGameLoanSize = TOTAL_GAMELOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_GAMELOAN_ID, HttpMethod.DELETE, null,
                Void.class);

        ResponseEntity<List<GameLoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseTypeList);

        assertNotNull(response);
        assertEquals(newGameLoanSize, response.getBody().size());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteGameLoanId = TOTAL_GAMELOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteGameLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
