package br.com.apirestcliente.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @project  api-client
 * Created by Leandro Saniago o
 * classe que vai retorar HttpStatus.NOT_FOUND caso não seja encontrado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundexeception extends RuntimeException {

    public ResourceNotFoundexeception(String message) {
        super(message);
    }
}
