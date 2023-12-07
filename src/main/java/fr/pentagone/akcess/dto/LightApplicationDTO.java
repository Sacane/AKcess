package fr.pentagone.akcess.dto;

import jakarta.validation.constraints.NotBlank;

public record LightApplicationDTO(int id, @NotBlank String name) {
}
