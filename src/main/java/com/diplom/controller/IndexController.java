package com.diplom.controller;

import com.diplom.dao.CategoryDao;
import com.diplom.dao.UserDao;
import com.diplom.model.Category;
import com.diplom.model.User;
import com.diplom.service.DatasetService;
import com.diplom.service.UserService;
import com.diplom.service.VideoService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Stole on 4/27/2016.
 */
@Controller
public class IndexController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
    public String index(Model model, HttpSession session) throws Exception {

        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }

        String queryTerm;

        if(session.getAttribute("user") != null)
        {
            List<Category> favs = ((User) session.getAttribute("user")).getCategoryList();
            if(favs.isEmpty())
            {
                queryTerm = categoryDao.lastEntry().getName();
            }
            else {
                queryTerm = favs.get(favs.size() - 1).getName();
            }
        }
        else {
            queryTerm = categoryDao.lastEntry().getName();
        }

        model.addAttribute("searchQuery",queryTerm);
        model.addAttribute("videosYouTube",videoService.searchYoutube(queryTerm,9));
        model.addAttribute("videosVimeo",videoService.searchVimeo(queryTerm,9));

        return "index-" + session.getAttribute("lang");
    }

    @RequestMapping(value = "/lang",method = RequestMethod.GET)
    public String langChange(@RequestParam String path,@RequestParam String lang, HttpSession session)
    {
        session.setAttribute("lang",lang);

        return "redirect:" + path;
    }

    @RequestMapping(value="*",method = RequestMethod.GET)
    public String error()
    {
        return "404";
    }

}
