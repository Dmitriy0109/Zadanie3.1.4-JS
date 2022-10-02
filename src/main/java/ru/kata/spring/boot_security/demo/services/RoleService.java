package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import java.util.List;
import java.util.Set;
@Service
public interface RoleService {
    List<Role> findAllRoles();

    Set<Role> getSetOfRoles(String[] roleNames);
}
