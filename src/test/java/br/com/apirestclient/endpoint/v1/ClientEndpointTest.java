package br.com.apirestclient.endpoint.v1;

import br.com.apirestcliente.model.Client;
import br.com.apirestcliente.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;

/**
 * @project api-client
 * Created by Leandro Saniago
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientEndpointTest {

    @MockBean
    private ClientRepository clientRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    //para seguranca
    private HttpEntity<Void> clientHeader;
    private HttpEntity<Void> wrongHeader;
    private Client client = mockCourse();

    //metodo para mokar um curso
    public static Client mockCourse(){

        return Client.Builder.newClient()
                .id(1L)
                .name("Leandro")
                .email("leandro19br@gmail.com")
                .address("Rua Aclimação")
                .cpf("29756885244")
                .build();

    }

    //Headers para gerar o token
    @Before
    public void configProfessorHeader(){

        String body = "{\"userName\" : \"leandro\",\"passWord\" : \"santiago\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", body, String.class).getHeaders();
        this.clientHeader = new HttpEntity<>(headers);
    }

    //add authorization errado
    @Before
    public void configWrongHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "11111");
       this.wrongHeader = new HttpEntity<>(headers);
    }


    @Before
    public void setup(){

        BDDMockito.when(clientRepository.findOne(client.getId())).thenReturn(client);
        BDDMockito.when(clientRepository.lisCoursesByName("")).thenReturn(Collections.singletonList(client));
        BDDMockito.when(clientRepository.lisCoursesByName("Leandro")).thenReturn(Collections.singletonList(client));

    }

    @Test
    public void deveRetornarStatus403QuandoBuscarCoursePorIdETokenErrado(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/1", GET, wrongHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deveRetornarStatus403QuandoBuscarListaCoursesTokenErrado(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/list?name=", GET, wrongHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deveRetornarListaVaziaQandoBuscarNomeQueNaoExiste(){
        ResponseEntity<List<Client>> exchange = restTemplate.exchange("/v1/client/list?name=xaxa", GET, clientHeader,
                new ParameterizedTypeReference<List<Client>>() {
                });
        assertThat(exchange.getBody()).isEmpty();
    }

    @Test
    public void deveRetornarStatus200QuandoBuscarListaCoursesEhNomeExiste(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/list?name=Leandro", GET, clientHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deveRetornarStatus400QuandoBuscarCoursePorIdENaoPassarId(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/", GET, clientHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarStatus404QuandoBuscarCoursePorIdENaoExiste(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/-1", GET, clientHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deveRetornarStatus200QuandoBuscarCoursePorIdExistente(){
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/1", GET, clientHeader, String.class);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deveRetornarStatus200QuandDeletarCoursePorIdExistente(){
        long id = 1L;
        BDDMockito.doNothing().when(clientRepository).delete(id);
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/{id}", DELETE, clientHeader, String.class,id);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deveRetornarStatus404QuandDeletarCoursePorIdNaoExistente(){
        long id = -1L;
        BDDMockito.doNothing().when(clientRepository).delete(id);
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/client/{id}", DELETE, clientHeader, String.class,id);
        assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deveRetornarStatus400QuandCriarCourseComNomeNull(){
        Client client = clientRepository.findOne(1L);
        client.setName(null);
        assertThat(criarCourse(client).getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void deveRetornarStatus200QuandCriarCourseComIdNull(){
        Client client = clientRepository.findOne(1L);
        client.setId(null);
        assertThat(criarCourse(client).getStatusCodeValue()).isEqualTo(200);
    }

    public ResponseEntity<String> criarCourse(Client client){
        BDDMockito.when(clientRepository.save(client)).thenReturn(client);
        return restTemplate.exchange("/v1/client/", POST, new HttpEntity<>(client,clientHeader.getHeaders()),String.class);
    }


}