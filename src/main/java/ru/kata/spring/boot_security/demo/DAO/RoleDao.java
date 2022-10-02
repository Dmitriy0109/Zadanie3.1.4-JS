package ru.kata.spring.boot_security.demo.DAO;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import java.util.List;
import java.util.Set;


@Repository
public interface RoleDao {
    List<Role> findAllRoles();

    Role getRole(String role);

    public Set<Role> getSetOfRoles(String[] roleNames);
}

