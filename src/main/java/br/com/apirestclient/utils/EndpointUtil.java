package br.com.apirestclient.utils;


import br.com.apirestclient.exeption.ResourceNotFoundexeception;
import br.com.apirestclient.model.ApplicationUser;
import br.com.apirestclient.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @project api-client
 * Created by Leandro Saniago on 12/02/2021 - 18:02.
 * classe que tem um metodo para auxiliar na return dos Endpoints
 */

@Service
public class EndpointUtil implements Serializable {


    public ResponseEntity<?> returnObjectOrNotFound(Object object) {
        if (object == null) throw new ResourceNotFoundexeception("Not Found ");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public ResponseEntity<?> returnObjectOrNotFound(List<?> list) {

        if (list == null || list.isEmpty()) throw new ResourceNotFoundexeception("Not Found");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //metodo para extrair o profesor do Authentication
    public Client extractProfessorForToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((ApplicationUser) authentication.getPrincipal()).getClient();
    }

}
