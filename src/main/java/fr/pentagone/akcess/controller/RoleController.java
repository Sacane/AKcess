package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.RoleDTO;
import fr.pentagone.akcess.dto.RoleSaveDTO;
import fr.pentagone.akcess.repository.sql.Role;
import fr.pentagone.akcess.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
import java.util.List;


@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable("roleId") int roleId) {
        LOGGER.info("Getting infos of role with id : " + roleId);
        return roleService.getRole(roleId);
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<List<RoleDTO>> getAllApplicationRoles(@PathVariable("applicationId") int applicationId) {
        LOGGER.info("Getting infos of roles with application id : " + applicationId);
        return roleService.getAllApplicationRoles(applicationId);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRoleFromApplication(@PathVariable("roleId") int roleId) {
        LOGGER.info("Deleting role with id : " + roleId);
        return roleService.deleteRoleFromApplication(roleId, 0);
    }

    @PatchMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("roleId") int roleId, @RequestBody RoleDTO inputRole) {
        LOGGER.info("Updating role with id : " + roleId);
        return roleService.updateRole(roleId, inputRole);
    }

    @PostMapping("/{applicationId}")
    public ResponseEntity<RoleDTO> create(@PathVariable("applicationId") int applicationId, @RequestBody RoleSaveDTO roleSaveDTO) {
        LOGGER.info("creating a role");
        return roleService.createRole(applicationId, roleSaveDTO);
    }

}
