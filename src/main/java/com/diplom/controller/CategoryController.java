package com.diplom.controller;

import com.diplom.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 * Created by Stole on 5/25/2016.
 */
@Controller
public class CategoryController {

    @Autowired
    private DatasetService service;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String getCat(@RequestParam String sc, HttpSession session,Model model) throws MalformedURLException, FileNotFoundException {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        model.addAttribute("sc",sc);
        model.addAttribute("categories",service.getDepts(sc));
        return "category-" + session.getAttribute("lang");
    }
}
