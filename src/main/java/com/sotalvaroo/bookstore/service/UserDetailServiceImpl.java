package com.sotalvaroo.bookstore.service;

import com.sotalvaroo.bookstore.entities.Permission;
import com.sotalvaroo.bookstore.entities.Role;
import com.sotalvaroo.bookstore.entities.StoreUser;
import com.sotalvaroo.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailsService {


    private final IUserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(@Qualifier("userRepository") IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StoreUser user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        return new User(user.getUsername(),user.getPassword(), getAuthorities(user.getRoles()));
    }

    private Set<? extends GrantedAuthority> getAuthorities(
            Set<Role> roles) {

        return getGrantedAuthorities(getPermissions(roles));
    }

    private Set<String> getPermissions(Set<Role> roles) {

        //TODO: Revisar en debug

        Set<String> privileges = new HashSet<>();
        Set<Permission> collection = new HashSet<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private Set<GrantedAuthority> getGrantedAuthorities(Set<String> permissions) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String privilege : permissions) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
