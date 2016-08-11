package com.diplom.controller;

import com.diplom.dao.CategoryDao;
import com.diplom.dao.UserDao;
import com.diplom.model.Category;
import com.diplom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stole on 7/19/2016.
 */
@RestController
public class CatController {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/favcat", method = RequestMethod.POST, produces = "text/plain")
    public String categoryLike(@RequestParam String catname, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null)
        return "";

        Category cat = categoryDao.findByName(catname);
        if(cat == null)
        {
            cat = new Category();
            cat.setName(catname);
        }
        if(cat.getUserList() == null)
        {
            cat.setUserList(new ArrayList<>());
        }
        cat.getUserList().add(user);
        categoryDao.save(cat);
        user = userDao.findOne(user.getId());
        session.setAttribute("user",user);
        return "Done";
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.GET, produces = "text/plain")
    public String favorites(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null)
            return "Not loged in";
        List<Category> cats = user.getCategoryList();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < user.getCategoryList().size(); i++)
        {
            sb.append(cats.get(i).getName());
        }
        return sb.toString();
    }
}
