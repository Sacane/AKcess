package fr.pentagone.akcess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDTO(int id, @NotNull @NotBlank String label) {
}
