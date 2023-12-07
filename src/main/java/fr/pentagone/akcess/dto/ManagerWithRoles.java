package fr.pentagone.akcess.dto;

import java.util.List;

public record ManagerWithRoles(int managerId, List<RoleDTO> roles) {
}
