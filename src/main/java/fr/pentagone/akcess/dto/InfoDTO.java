package fr.pentagone.akcess.dto;

import java.util.List;

public record InfoDTO(String name, String version, List<String> cve) {
}
