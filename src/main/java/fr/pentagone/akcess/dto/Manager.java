package fr.pentagone.akcess.dto;

import java.util.List;

public record Manager(CredentialsDTO credentialsDTO, List<RoleDTO> roles) {
}
