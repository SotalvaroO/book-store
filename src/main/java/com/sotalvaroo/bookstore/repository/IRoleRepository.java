package com.sotalvaroo.bookstore.repository;

import com.sotalvaroo.bookstore.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
