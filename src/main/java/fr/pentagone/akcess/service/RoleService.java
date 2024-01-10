package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.RoleDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<RoleDTO> getRole(int roleId) {
        var optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            throw HttpException.badRequest("Role not found");
        }
        var role = optionalRole.get();

        return ResponseEntity.ok(new RoleDTO(role.getId(), role.getName(), role.getApplication().getId()));
    }
}
