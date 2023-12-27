package fr.pentagone.akcess.dto;

import jakarta.validation.constraints.NotBlank;

public record LightApplicationDTO(@NotBlank String name, @NotBlank String url) {
}
