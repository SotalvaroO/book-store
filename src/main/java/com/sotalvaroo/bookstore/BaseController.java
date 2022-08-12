package com.sotalvaroo.bookstore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/base")
public class BaseController {

    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok().build();
    }

}
