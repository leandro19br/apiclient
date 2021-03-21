package br.com.apirestclient.model;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends AbstractEntity {

    @NotEmpty(message = "the field name cannot be empty")
    private String name;
    @NotEmpty(message = "the field address cannot be empty")
    private String address;
    @NotEmpty(message = "the field cpf cannot be empty")
    private String cpf;
    @Email(message = "email is not valid")
    @NotEmpty(message = "the field email cannot be empty")
    @Column(unique = true)
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static final class Builder {
        private Client client;

        private Builder() {
            client = new Client();
        }

        public static Builder newClient() {
            return new Builder();
        }

        public Builder name(String name) {
            client.setName(name);
            return this;
        }

        public Builder id(Long id) {
            client.setId(id);
            return this;
        }

        public Builder email(String email) {
            client.setEmail(email);
            return this;
        }

        public Builder address(String address) {
            client.setAddress(address);
            return this;
        }

        public Builder cpf(String cpf) {
            client.setCpf(cpf);
            return this;
        }

        public Client build() {
            return client;
        }
    }
}
