package com.sotalvaroo.bookstore.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private StoreUser owner;

    @OneToMany
    private List<StoreUser> workers;

    public Store() {
    }
}
