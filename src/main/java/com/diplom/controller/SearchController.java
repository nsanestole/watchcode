package com.diplom.controller;

import com.diplom.model.Category;
import com.diplom.model.User;
import com.diplom.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Stole on 4/28/2016.
 */
@Controller
public class SearchController {

    @Autowired
    private VideoService videoService;


    @RequestMapping(value="/search",method = RequestMethod.GET)
    public String search(@RequestParam String q, Model model, HttpSession session) throws Exception {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        model.addAttribute("queryTerm",q);
        model.addAttribute("videosYouTube",videoService.searchYoutube(q,6));
        model.addAttribute("videosVimeo",videoService.searchVimeo(q,6));

        User user = (User) session.getAttribute("user");
        if(user != null)
        {
            List<Category> cats = user.getCategoryList();
            int flag = 0;
            for(int i = 0; i < cats.size(); i++)
            {
                if(cats.get(i).getName().equals(q))
                {
                    flag = 1;
                }
            }
            if(flag == 1)
            {
                model.addAttribute("note","has");
            }
            else model.addAttribute("note","not");
        }

        return "search-" + session.getAttribute("lang");
    }

}
