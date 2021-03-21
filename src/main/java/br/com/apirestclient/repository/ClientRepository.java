package br.com.apirestclient.repository;

import br.com.apirestclient.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @project api-client
 * Created by Leandro Saniago
 */
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {


    @Query(value = "select c from Client c where c.name like %?1%")
    List<Client> lisCoursesByName(String name);


}
