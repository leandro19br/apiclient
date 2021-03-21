package br.com.apirestcliente.repository;


import br.com.apirestcliente.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @project api-client
 * Created by Leandro Saniago
 */
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    ApplicationUser findByUserName(String userName);

}
