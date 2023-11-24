package br.com.hebio.desafiofd.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRecord(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @Email
        String email,

        @Size(min = 6, max = 20)
        String password,

        @Size(min = 6, max = 20)
        String passwordConfirmation
) {
}
