package fr.pentagone.akcess.dto;

import javax.validation.constraints.NotBlank;

public record LightApplicationDTO(int id, @NotBlank String name) {
}
