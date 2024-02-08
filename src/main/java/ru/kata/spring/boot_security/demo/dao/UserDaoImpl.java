package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isTableUsersEmpty() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User ");
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Long addUser(User user) {
        if (getUserByName(user.getEmail()).isEmpty()) {
            entityManager.persist(user);
        }
        return user.getId();
    }

    @Override
    public void editUser(User userDetails, Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setLastname(userDetails.getLastname());
            user.setAge(userDetails.getAge());
            if (getUserByName(userDetails.getEmail()).isEmpty()) {
                user.setEmail(userDetails.getEmail());
            }
        } else {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
        entityManager.remove(user);
    }

    @Override
    public Optional<User> getUserByName(String email) {
        String query = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email";
        try {
            User user = entityManager.createQuery(query, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
            //throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
    }

    @Override
    public User getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
        return user;
    }
}
