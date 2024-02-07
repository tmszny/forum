package com.Forum.data;

import com.Forum.security.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegesRepository extends CrudRepository<Privilege, Long> {

    Privilege findByName(String name);
}
