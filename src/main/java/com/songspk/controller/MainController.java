package com.songspk.controller;

import com.songspk.global.GenericDB;
import com.songspk.entity.Song;
import com.songspk.tables.Songs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: rishabh
 */
@Controller
@RequestMapping("/song")
public class MainController extends BaseController {

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String show(){
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/top")
    public @ResponseBody String showTop(){
        return "Top songs will be updated soon";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public @ResponseBody String showNew(){
        return "New songs will be updated soon";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{id}")
    public String searchSong(@PathVariable("id") Long songId, ModelMap map){
        Song x = new GenericDB<Song>().getRow(Songs.SONGS,Song.class,Songs.SONGS.ID.eq(songId));
        map.addAttribute("LINK",x.link);
        map.addAttribute("NAME",x.name);
        return "index";
    }
}