package no.oslomet.clienttwitter.service;

import no.oslomet.clienttwitter.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    String BASE_URL = "http://localhost:9070/roles";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Role> getAllRoles() {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Role[].class)
        ).collect(Collectors.toList());
    }

    public Role getRoleById(long id) {
        Role role = restTemplate.getForObject(BASE_URL + "/" + id, Role.class);
        return role;
    }
}
