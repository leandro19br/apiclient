package br.com.apirestclient.service;


import br.com.apirestclient.model.ApplicationUser;
import br.com.apirestclient.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @project api-client
 * Created by Leandro Saniago
 * classe responsável pelo acesso ao usuário e senha no BD
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public CustomUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ApplicationUser applicationUser = loadApplicationUserByUserName(userName);
        return new CustomUserDatails(applicationUser) ;
    }


    public ApplicationUser loadApplicationUserByUserName(String userName) {
        return Optional.ofNullable(applicationUserRepository.findByUserName(userName))
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found"));
    }

    private final static class CustomUserDatails extends ApplicationUser implements UserDetails {

        private CustomUserDatails(ApplicationUser applicationUser){
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> roleClient = AuthorityUtils.createAuthorityList("ROLE_CLIENT");
            return roleClient;
        }

        @Override
        public String getPassword() {
            return this.getPassWord();
        }

        @Override
        public String getUsername() {
            return this.getUserName();
        }


        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
