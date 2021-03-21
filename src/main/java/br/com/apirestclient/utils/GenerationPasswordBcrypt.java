package br.com.apirestclient.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @project api-client
 * Created by Leandro Saniago .
 */
public class GenerationPasswordBcrypt {

    public static void main(String[] args) {

        System.out.println(" *** Senha Criptografada " + new BCryptPasswordEncoder().encode("santiago"));

    }

}
