package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.RoleDTO;
import fr.pentagone.akcess.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable("roleId") int roleId) {
        return roleService.getRole(roleId);
    }
}
