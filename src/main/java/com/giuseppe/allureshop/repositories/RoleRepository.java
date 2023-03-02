package com.giuseppe.allureshop.repositories;

import com.giuseppe.allureshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String roleName);
    Role findRoleByName(String roleName);

}
