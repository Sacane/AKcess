package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.RoleDTO;
import fr.pentagone.akcess.dto.RoleSaveDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.Role;
import fr.pentagone.akcess.repository.sql.RoleRepository;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final ApplicationRepository applicationRepository;

    public RoleService(RoleRepository roleRepository, ApplicationRepository applicationRepository) {

        this.roleRepository = roleRepository;
        this.applicationRepository = applicationRepository;

    }

    @Transactional
    public ResponseEntity<RoleDTO> getRole(int roleId) {
        var optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            throw HttpException.notFound("Role not found");
        }
        var role = optionalRole.get();

        return ResponseEntity.ok(new RoleDTO(role.getId(), role.getName(), role.getApplication().getId()));
    }

    @Transactional
    public ResponseEntity<List<RoleDTO>> getAllApplicationRoles(int applicationId) {
        var optionalApplication =  applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            throw HttpException.notFound("Application not found");
        }
        var roles = optionalApplication.get().getRoles();
        List<RoleDTO> rolesDTO = roles.stream().map(role -> new RoleDTO(role.getId(), role.getName(), role.getApplication().getId())).toList();
        return ResponseEntity.ok(rolesDTO);
    }

    @Transactional
    public ResponseEntity<String> deleteRoleFromApplication(int roleId, int applicationId){
        var optionalApplication =  applicationRepository.findById(applicationId);
        if (roleRepository.findById(roleId).isEmpty()) {
            throw HttpException.notFound("Role not found");
        }
        if (optionalApplication.isEmpty()) {
            throw HttpException.notFound("Application not found");
        }
        var application = optionalApplication.get();
        application.deleteRole(roleId);
        roleRepository.deleteById(roleId);
        applicationRepository.save(application);
        return ResponseEntity.ok("Role has been deleted successfully.");
    }


    public ResponseEntity<RoleDTO> updateRole(int roleId, RoleDTO inputRole) {
        var optionalRole = roleRepository.findById(roleId);
        var optionalApplication =  applicationRepository.findById(inputRole.applicationId());
        if (optionalRole.isEmpty()) {
            throw HttpException.notFound("Role not found");
        }
        if (optionalApplication.isEmpty()) {
            throw HttpException.notFound("Application not found");
        }
        var role = optionalRole.get();
        var application = optionalApplication.get();
        role.update(inputRole, application);
        roleRepository.save(role);
        return ResponseEntity.ok(new RoleDTO(role.getId(), role.getName(), role.getApplication().getId()));


    }

    @Transactional
    public ResponseEntity<RoleDTO> createRole(int applicationId, RoleSaveDTO roleSaveDTO) {
        var appOpt = applicationRepository.findById(applicationId);
        if(appOpt.isEmpty()) throw HttpException.notFound("The application does not exists");
        var app = appOpt.get();
        var role = new Role(roleSaveDTO.label());
        var saved = roleRepository.save(role);
        app.addRole(role);
        return ResponseEntity.ok(new RoleDTO(saved.getId(), saved.getName(), saved.getApplication().getId()));
    }
}
