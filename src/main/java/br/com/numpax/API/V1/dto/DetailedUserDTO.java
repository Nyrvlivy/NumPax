package br.com.numpax.API.V1.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DetailedUserDTO {
    private String id;
    private String name;
    private String email;
    private LocalDate birthdate;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DetailedUserDTO(String id, String name, String email, LocalDate birthdate, boolean isActive,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getBirthdate() { return birthdate; }
    public boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return String.format(
            "DetailedUserDTO {\n" +
                "  id: '%s',\n" +
                "  name: '%s',\n" +
                "  email: '%s',\n" +
                "  birthdate: %s,\n" +
                "  isActive: %s,\n" +
                "  createdAt: %s,\n" +
                "  updatedAt: %s\n" +
                "}",
            id, name, email, birthdate, isActive, createdAt, updatedAt
        );
    }


}
