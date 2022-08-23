package com.sotalvaroo.bookstore.repository;

import com.sotalvaroo.bookstore.entities.StoreUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface IUserRepository extends JpaRepository<StoreUser, Long> {

    StoreUser findByUsername(String username);

}
