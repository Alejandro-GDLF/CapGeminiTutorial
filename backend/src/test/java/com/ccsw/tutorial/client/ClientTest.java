package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.NameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    public static final Long EXISTS_CLIENT_ID = 1L;
    public static final Long NOT_EXISTS_CLIENT_ID = 0L;

    @Test
    public void getExistingClientIdShouldReturnClient() {
        Client client = mock(Client.class);

        when(client.getId()).thenReturn(EXISTS_CLIENT_ID);

        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        Client clientResponse = clientService.get(EXISTS_CLIENT_ID);

        assertNotNull(clientResponse);
        assertEquals(EXISTS_CLIENT_ID, client.getId());
    }

    @Test
    public void getNonExistingClientIdShouldReturnNull() {
        when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());
        Client client = clientService.get(NOT_EXISTS_CLIENT_ID);

        assertNull(client);
    }

    @Test
    public void findAllShouldReturnAllClients() {

        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    public static final String CLIENT_NAME = "CLI1";
    public static final long CLIENT_EXISTS = 1L;
    public static final long CLIENT_DONT_EXISTS = 0L;

    @Test
    public void saveRepeatedNameShouldThrow() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        doReturn(CLIENT_EXISTS).when(clientRepository).count(Mockito.any(ClientSpecification.class));

        assertThrows(NameAlreadyExistsException.class, () -> clientService.save(clientDto));
    }

    @Test
    public void saveNotRepeatedNameShouldSave() throws NameAlreadyExistsException {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);
        doReturn(CLIENT_DONT_EXISTS).when(clientRepository).count(Mockito.any(ClientSpecification.class));

        clientService.save(clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    @Test
    public void updateRepeatedNameShouldThrow() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);

        doReturn(Optional.of(client)).when(clientRepository).findById(EXISTS_CLIENT_ID);

        doReturn(CLIENT_EXISTS).when(clientRepository).count(Mockito.any(ClientSpecification.class));

        assertThrows(NameAlreadyExistsException.class, () -> clientService.update(EXISTS_CLIENT_ID, clientDto));
    }

    @Test
    public void updateNotRepeatedNameShouldSave() throws NameAlreadyExistsException {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);
        doReturn(CLIENT_DONT_EXISTS).when(clientRepository).count(Mockito.any(ClientSpecification.class));

        clientService.save(clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    @Test
    public void updateNonExistingClientShouldThrow() {
        when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

        ClientDto clientDto = new ClientDto();
        assertThrows(Exception.class, () -> clientService.update(NOT_EXISTS_CLIENT_ID, clientDto));
    }

    @Test
    public void deleteExistentClientIdShouldDelete() throws Exception {
        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }

    @Test
    public void deleteNonExistentClientIdShouldThrow() throws Exception {
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clientService.delete(EXISTS_CLIENT_ID));
    }
}
