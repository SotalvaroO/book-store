package com.sotalvaroo.bookstore.controller;

import com.sotalvaroo.bookstore.repository.IStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private IStoreRepository storeRepository;

    @GetMapping
    public ResponseEntity<?> getStores(){
        return ResponseEntity.ok(storeRepository.findAll());
    }

}
