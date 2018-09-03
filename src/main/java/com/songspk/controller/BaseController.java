package com.songspk.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rishabh.
 */
public class BaseController {
    @ExceptionHandler
    public
    @ResponseBody
    String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        e.printStackTrace();
        return "error";
    }
}
