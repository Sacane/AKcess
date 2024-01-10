package fr.pentagone.akcess.dto;

import jakarta.validation.constraints.NotBlank;

public record PostApplicationDTO(@NotBlank int idempotencyKey, @NotBlank String name, @NotBlank String url) { }
