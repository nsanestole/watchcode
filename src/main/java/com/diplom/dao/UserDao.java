package com.diplom.dao;

import com.diplom.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Stole on 4/27/2016.
 */
@Transactional
public interface UserDao extends CrudRepository<User,Integer> {

    public User findByEmail(String email);

    public User findByEmailAndPassword(String email,String password);
}
