package com.sotalvaroo.bookstore.repository;

import com.sotalvaroo.bookstore.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission,Long> {

    Permission findByName(String name);

}
