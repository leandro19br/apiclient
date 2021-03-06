package br.com.apirestclient.filtro;

import br.com.apirestclient.model.ApplicationUser;
import br.com.apirestclient.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.apirestclient.constants.Constants.*;

/**
 * @project api-client
 * Created by Leandro Saniago
 * classe responsável pela autorização de usuário a utilizar os endpoints
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager
            , CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //conteudo do HEADER
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken autheticationtoken = getAutheticationtoken(request);
        SecurityContextHolder.getContext().setAuthentication(autheticationtoken);
        chain.doFilter(request,response);
    }


    private UsernamePasswordAuthenticationToken getAutheticationtoken(HttpServletRequest request) {

        String token = request.getHeader(HEADER_STRING);

        if (token == null) return null;

        String userName = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        ApplicationUser applicationUser = customUserDetailsService.loadApplicationUserByUserName(userName);
        return userDetails != null ?
                new UsernamePasswordAuthenticationToken(applicationUser, null, userDetails.getAuthorities()) : null;
    }

}
