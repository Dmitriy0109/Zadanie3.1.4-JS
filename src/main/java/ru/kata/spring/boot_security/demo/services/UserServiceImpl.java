package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }


    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            if (userDao.findUserByEmail(user.getEmail()) != null) {
                throw new NonUniqueResultException("Ошибка: логин '" + user.getEmail() + "' уже занят.");
            }
        } catch (EmptyResultDataAccessException | NoResultException ignored) {
        }
        userDao.save(user);
    }


    @Override
    @Transactional
    public void update(int id, User updateUser) {

        User user = userDao.getById(id);

        if (updateUser.getPassword().equals(user.getPassword())) {
            userDao.update(updateUser);
        } else {
            String pass = passwordEncoder.encode(updateUser.getPassword());
            updateUser.setPassword(pass);
            userDao.update(updateUser);
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User getById(int id) {
        Optional<User> foundUser = Optional.ofNullable(userDao.getById(id));
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
