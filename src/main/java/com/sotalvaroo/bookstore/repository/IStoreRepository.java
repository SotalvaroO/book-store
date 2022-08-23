package com.sotalvaroo.bookstore.repository;

import com.sotalvaroo.bookstore.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStoreRepository extends JpaRepository<Store,Long> {

    Store findByName(String name);

}
