package br.com.numpax.infrastructure.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthdate;
    private Boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String name, String email, String password, LocalDate birthdate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
        this.birthdate = birthdate;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String id, String name, String email, String password, LocalDate birthdate, Boolean isActive,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password != null ? password : "";
        this.birthdate = birthdate;
        this.isActive = isActive != null ? isActive : true;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
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

    public void setPassword(String password) {
        this.password = hashPassword(password);
        this.updatedAt = LocalDateTime.now();
    }

    // Método para retornar a senha criptografada
    public String getHashedPassword() {
        return password;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
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

    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(hashPassword(inputPassword));
    }
}
