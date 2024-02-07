package com.Forum.data;

import com.Forum.security.Privilege;
import com.Forum.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
