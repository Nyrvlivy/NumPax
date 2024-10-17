package br.com.numpax.API.V1.dto;

import java.time.LocalDate;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private LocalDate birthdate;
    private String password;

    public UserDTO(String id, String name, String email, LocalDate birthdate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.password = password;
    }

    public UserDTO(String id, String name, String email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getBirthdate() { return birthdate; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return String.format(
            "UserDTO{\n" +
                "  id: '%s',\n" +
                "  name: '%s',\n" +
                "  email: '%s',\n" +
                "  birthdate: %s\n" +
                "}",
            id, name, email, birthdate
        );
    }
}
