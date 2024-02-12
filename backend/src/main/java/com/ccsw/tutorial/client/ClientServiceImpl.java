package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.criteria.SearchCriteria;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * Devuelve cuántos {@link Client} hay a partir de su nombre
     *
     * @param name nombre de la entidad
     * @return {@link Long}
     */
    private Long getClientCount(String name) {
        ClientSpecification spec = new ClientSpecification(new SearchCriteria("name", ":", name));

        return this.clientRepository.count(spec);
    }

    /**
     * Devuelve si existe algún {@link Client} con el mismo nombre.
     * 
     * @param name Nombre de la entidad
     * @return {@Link Boolean}
     */
    private Boolean doesNameExists(String name) {
        return this.getClientCount(name) > 0;
    }

    /**
     * Comprueba las restricciones de nombre. Si no se cumplen lanza una
     * {@link DataIntegrityViolationException}
     * 
     * @param name Nombre de la entidad
     * @throws {@link DataIntegrityViolationException}
     */
    private void checkNameContraints(String name) throws DataIntegrityViolationException {
        // Check for repeated names
        if (this.doesNameExists(name)) {
            // Throw a 409 Conflict response code
            throw new DataIntegrityViolationException("Name already exists");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(ClientDto dto) {
        this.checkNameContraints(dto.getName());

        Client client = new Client();

        client.setName(dto.getName());

        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Long id, ClientDto dto) throws Exception {
        Client client = this.get(id);
        if (client == null) {
            throw new Exception("Not exists");
        }

        this.checkNameContraints(dto.getName());

        client.setName(dto.getName());

        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clientRepository.deleteById(id);

    }

}
