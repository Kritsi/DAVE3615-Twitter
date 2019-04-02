package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.Role;
import no.oslomet.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String home(){
        return "This is a rest controller ";
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable long id){
        return roleService.getRoleById(id);
    }

}
