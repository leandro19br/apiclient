package br.com.apirestcliente.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
public class ApplicationUser extends AbstractEntity {

    @NotEmpty(message ="field userName cannot be null")
    @Column(unique = true)
    private String userName;
    @NotEmpty(message ="field passWord cannot be null")
    private String passWord;
    @OneToOne
    @JsonIgnore
    private Client client;

    public ApplicationUser() {
    }

    public ApplicationUser(ApplicationUser applicationUser) {
        this.userName = applicationUser.userName;
        this.passWord = applicationUser.passWord;
        this.client = applicationUser.client;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
