package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Role;
import no.oslomet.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(long id){
        return roleRepository.findById(id).get();
    }

}
