package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAllRoles() {
        List<Role> roleList = entityManager.createQuery("select role from Role role", Role.class).getResultList();
        return roleList;
    }

    @Override
    public Role getRole(String role) {
        return entityManager.createQuery("select r from Role r where r.role =: role", Role.class)
                .setParameter("role", role).getSingleResult();
    }

    @Override
    public Set<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleNames) {
            roleSet.add(getRole(role));
        }
        return roleSet;
    }
}
