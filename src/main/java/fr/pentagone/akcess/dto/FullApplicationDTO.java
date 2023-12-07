package fr.pentagone.akcess.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record FullApplicationDTO(
        int id,
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String rootUrl,
        List<UserDTO> users,
        List<RoleDTO> roles
) {
}
