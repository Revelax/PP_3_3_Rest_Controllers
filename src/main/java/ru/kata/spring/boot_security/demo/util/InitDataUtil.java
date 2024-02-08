package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;


@Component
public class InitDataUtil {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitDataUtil(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        if (userService.isTableUsersEmpty()) {
            List<Role> roles = roleService.getAllRole();

            User user1 = new User();
            user1.setUsername("admin");
            user1.setLastname("adminov");
            user1.setAge((byte) 30);
            user1.setEmail("admin@mail.ru");
            user1.setPassword("admin");
            user1.setRoles(Collections.singleton(roles.get(1)));

            User user2 = new User();
            user2.setUsername("user");
            user2.setLastname("userov");
            user2.setAge((byte) 30);
            user2.setEmail("user@mail.ru");
            user2.setPassword("user");
            user2.setRoles(Collections.singleton(roles.get(0)));

            userService.add(user1);
            userService.add(user2);
        }
    }
}


