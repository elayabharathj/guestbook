package com.guestbook.guestapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GuestBookErrorController implements ErrorController {
    private static Logger LOGGER = LoggerFactory.getLogger(GuestBookErrorController.class);
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        LOGGER.info("Entering into handleError()");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            LOGGER.info("Error Code" + status.toString());
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-403";
            }
        }
        LOGGER.info("Leaving handleError()");
        return "error";
    }
}