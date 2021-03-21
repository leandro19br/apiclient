package br.com.apirestclient.repository;


import br.com.apirestclient.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @project api-client
 * Created by Leandro Saniago
 */
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    ApplicationUser findByUserName(String userName);

}
