package fr.pentagone.akcess.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Api to server
public record UserDTO(@NotNull @NotBlank String username, @NotNull @NotBlank String password) {
}
