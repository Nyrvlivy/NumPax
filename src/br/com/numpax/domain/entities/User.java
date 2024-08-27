package br.com.numpax.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public class User {
    private final String id;                  // Identificador único
    private String name;                      // Nome completo
    private String email;                     // E-mail
    private String password;                  // Senha
    private LocalDate birthdate;              // Data de nascimento
    private boolean isActive;                 // Ativo ou Inativo
    private final LocalDateTime createdAt;          // Data de criação
    private LocalDateTime updatedAt;          // Data de atualização

    public User(String id, String name, String email, String password, LocalDate birthdate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        this.updatedAt = LocalDateTime.now();
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setIsActive(boolean active) {
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
}

public boolean checkPassword(String inputPassword) {
    return this.password.equals(inputPassword);
}
}
