package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.RoleDTO;
import fr.pentagone.akcess.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{applicationId}")
    public ResponseEntity<List<RoleDTO>> getAllApplicationRoles(@PathVariable("applicationId") int applicationId) {
        LOGGER.info("Getting infos of roles with application id : " + applicationId);
        return roleService.getAllApplicationRoles(applicationId);
    }

    @DeleteMapping("/{roleId}/aplication/{applicationId}")
    public ResponseEntity<String> deleteRoleFromApplication(@PathVariable("roleId") int roleId, @PathVariable("applicationId") int applicationId) {
        LOGGER.info("Deleting role with id : " + roleId + " from application with id : " + applicationId);
        return roleService.deleteRoleFromApplication(roleId, applicationId);
    }

    @PatchMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("roleId") int roleId, @RequestBody RoleDTO inputRole) {
        LOGGER.info("Updating role with id : " + roleId);
        return roleService.updateRole(roleId, inputRole);
    }


}
