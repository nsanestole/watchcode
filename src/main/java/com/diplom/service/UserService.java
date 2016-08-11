package com.diplom.service;

import com.diplom.dao.UserDao;
import com.diplom.dao.VimeoVideoDao;
import com.diplom.model.User;
import com.diplom.model.VimeoVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Created by Stole on 4/27/2016.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private VimeoVideoDao vimeoVideoDao;

    public User login(String email,String password)
    {
        return userDao.findByEmailAndPassword(email,password);
    }

    public Boolean checkUserExist(String email)
    {

        if(userDao.findByEmail(email) != null)
        {
            return true;
        }
        else return false;
    }

    public void createUser( String email,  String password,  String name,  String lastname,  Date birthdate, String location, String profile)
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setLastname(lastname);
        user.setBirthdate(birthdate);
        user.setLocation(location);
        user.setProfile(profile);
        userDao.save(user);
    }

    public List<VimeoVideo> lastViewed(Integer userId)
    {
        return vimeoVideoDao.lastViewed(userId);
    }

    public List<VimeoVideo> getHistory(Integer userId) {return vimeoVideoDao.getHistory(userId); }
}
