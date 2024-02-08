package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void update(User updatedUser, Long id) {
        User user = findUserById(id);
        if (user != null) {
            if (!updatedUser.getRoles().isEmpty()) {
                user.setRoles(updatedUser.getRoles());
            }
            if (!updatedUser.getPassword().isEmpty() && !user.getPassword().equals(updatedUser.getPassword())) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
        }
        userDao.editUser(updatedUser, id);
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public boolean isTableUsersEmpty() {
        return userDao.isTableUsersEmpty();
    }
}