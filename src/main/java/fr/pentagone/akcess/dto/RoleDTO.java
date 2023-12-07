package fr.pentagone.akcess.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RoleDTO(int id, @NotNull @NotBlank String label) {
}
