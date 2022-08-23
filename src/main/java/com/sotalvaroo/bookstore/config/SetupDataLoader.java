package com.sotalvaroo.bookstore.config;

import com.sotalvaroo.bookstore.entities.Permission;
import com.sotalvaroo.bookstore.entities.Role;
import com.sotalvaroo.bookstore.entities.Store;
import com.sotalvaroo.bookstore.entities.StoreUser;
import com.sotalvaroo.bookstore.repository.IPermissionRepository;
import com.sotalvaroo.bookstore.repository.IRoleRepository;
import com.sotalvaroo.bookstore.repository.IStoreRepository;
import com.sotalvaroo.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private IStoreRepository storeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Permission readPrivilege = createPermissionIfNotFound("READ_PRIVILEGE");
        final Permission writePrivilege = createPermissionIfNotFound("WRITE_PRIVILEGE");
        final Permission passwordPrivilege = createPermissionIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Permission> adminPermissions = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Permission> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(adminPermissions));
        final Role userRole = createRoleIfNotFound("ROLE_USER", new HashSet<>(userPrivileges));


        // == create initial user
        StoreUser sotalvaroo = createUserIfNotFound("sotalvaroo", "Test", new HashSet<>(Arrays.asList(adminRole)));
        createUserIfNotFound("cardacar", "Test", new HashSet<>(Arrays.asList(userRole)));

        createStoreIfNotFound("dabeiba-shop",sotalvaroo);

        alreadySetup = true;
    }

    @Transactional
    Permission createPermissionIfNotFound(final String name) {
        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission(name);
            permission = permissionRepository.save(permission);
        }
        return permission;
    }

    @Transactional
    Store createStoreIfNotFound(final String name,StoreUser user) {
        Store store = storeRepository.findByName(name);
        if (store == null) {
            store = new Store();
            store.setName(name);
            store.setUser(user);
            store = storeRepository.save(store);
        }
        return store;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Set<Permission> permissions) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.getPermissions().addAll(permissions);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    StoreUser createUserIfNotFound(String username, final String password, final Set<Role> roles) {
        StoreUser user = userRepository.findByUsername(username);
        if (user == null) {
            user = new StoreUser();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}
