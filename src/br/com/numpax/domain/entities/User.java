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
    private Boolean isActive;                 // Ativo ou Inativo
    private LocalDateTime createdAt;          // Data de criação
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
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
