package br.com.apirestcliente.endpoint.v1;

import br.com.apirestcliente.model.Client;
import br.com.apirestcliente.repository.ClientRepository;
import br.com.apirestcliente.service.ClientService;
import br.com.apirestcliente.utils.EndpointUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/client")
public class ClientEndpoint {

    private final EndpointUtil endpointUtil;
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientEndpoint(EndpointUtil endpointUtil, ClientService clientService, ClientRepository clientRepository) {
        this.endpointUtil = endpointUtil;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }


    @ApiOperation(value = "Retorn client by id")
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getCurseById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(clientRepository.findOne(id));
    }


    @ApiOperation(value = "Retorn list of client")
    @GetMapping(path = "list")
    public ResponseEntity<?> listCourses(@ApiParam("Client Name") @RequestParam(value = "name", defaultValue = "") String name) {
        return new ResponseEntity<>(clientRepository.lisCoursesByName(name),HttpStatus.OK);
    }

    @ApiOperation(value = "delete the client based on the id and retorn 200 OK")
    @DeleteMapping(path = "{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable long id) {
        clientService.throwResourceNotFoundIfCourseDoesNotExist(id);
        clientRepository.delete(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    //@Valid valida caso seja passado o nome em branco
    @ApiOperation(value = "update the client and retorn 200 ok ")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Client client) {
        clientService.throwResourceNotFoundIfCourseDoesNotExist(client);
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "create the client and retorn 200 ok ")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Client client) {
       return new ResponseEntity<>(clientRepository.save(client),HttpStatus.OK);
    }


}
