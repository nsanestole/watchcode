package com.diplom.controller;

import com.diplom.model.Category;
import com.diplom.model.User;
import com.diplom.service.UserService;
import com.diplom.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Created by Stole on 5/17/2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session)
    {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        User user = userService.login(email,password);
        if(user == null)
        {
            return "register-" + session.getAttribute("lang");
        }
        else
        {
            session.setAttribute("user",user);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpSession session)
    {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        return "login-" + session.getAttribute("lang");
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpSession session)
    {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        return "register-" + session.getAttribute("lang");
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String regUser(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String lastname, @RequestParam Date birthdate, @RequestParam String location, @RequestParam String profile, HttpSession session,Model model)
    {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        if(userService.checkUserExist(email))
        {
            model.addAttribute("message","User e-mail is taken.");
            return "register-" + session.getAttribute("lang");
        }
        else {
            userService.createUser(email, password, name, lastname, birthdate, location, profile);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(HttpSession session, Model model) throws IOException {
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            return "redirect:/login";
        }
        model.addAttribute("lastVideos", userService.lastViewed(user.getId()));
        List<Category> cats =  user.getCategoryList();
        for(int i = cats.size()-3,j = 0; i < cats.size(); i++,j++)
        {
            model.addAttribute("cattitle" + j,cats.get(i).getName());
            model.addAttribute("catvideos" + j, videoService.searchYoutube(cats.get(i).getName(),3));
        }
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        return "profile-" + session.getAttribute("lang");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String history(HttpSession session, Model model)
    {
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            return "redirect:/login";
        }
        model.addAttribute("videos",userService.getHistory(user.getId()));
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        return "history-" + session.getAttribute("lang");
    }
    
}
