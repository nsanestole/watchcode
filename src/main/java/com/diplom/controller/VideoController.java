package com.diplom.controller;

import com.diplom.dao.VimeoVideoDao;
import com.diplom.model.User;
import com.diplom.model.VimeoVideo;
import com.diplom.service.VideoService;
import com.google.api.services.youtube.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Stole on 5/16/2016.
 */
@Controller
public class VideoController {

    @Autowired
    private VideoService service;
    @Autowired
    private VimeoVideoDao vimeoVideoDao;

    @RequestMapping(value = "/watch",method = RequestMethod.GET)
    public String watch(@RequestParam String source, @RequestParam String id, HttpSession session,Model model) throws IOException {
        if(session.getAttribute("lang") == null)
        {
            session.setAttribute("lang","en");
        }
        VimeoVideo video = new VimeoVideo();
        if(source.equals("youtube"))
        {
            Video vyt = service.getVideoYoutube(id);
            video.setImageUri(vyt.getSnippet().getThumbnails().getMedium().getUrl());
            video.setVideoUri(vyt.getId());
            video.setName(vyt.getSnippet().getTitle());
            video.setSource("youtube");
            model.addAttribute("video",vyt);
            model.addAttribute("relatedVideos",service.relatedYoutube(id));
        }
        else {
            video = service.getVideoVimeo(id);
            video.setSource("vimeo");
            model.addAttribute("video",video);
            model.addAttribute("relatedVideos",service.relatedVimeo(id));
        }
        User user = (User) session.getAttribute("user");
        if(user != null)
        {
            video.setUser_id(user);
            video.setDescription("");
            if(vimeoVideoDao.findByVideoUriAndUser(video.getVideoUri(), user) == null)
            vimeoVideoDao.save(video);
        }

        return "watch-" + source + "-" + session.getAttribute("lang");
    }
}
