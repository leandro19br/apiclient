package br.com.apirestcliente.service;


import br.com.apirestcliente.exeption.ResourceNotFoundexeception;
import br.com.apirestcliente.model.Client;
import br.com.apirestcliente.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @project api-client
 * Created by Leandro Saniago
 * classe responsável por acessar o repository e buscar o cliente se não for encontrado lança exeption
 */
@Service
public class ClientService implements Serializable {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    public void throwResourceNotFoundIfCourseDoesNotExist(Client client){

        if (client == null || client.getId() == null || clientRepository.findOne(client.getId()) == null){

            throw new ResourceNotFoundexeception("client not found");
        }

    }

    public void throwResourceNotFoundIfCourseDoesNotExist(long clientId){

        if (clientId == 0 || clientRepository.findOne(clientId) == null){

            throw new ResourceNotFoundexeception("client not  found");
        }

    }


}
