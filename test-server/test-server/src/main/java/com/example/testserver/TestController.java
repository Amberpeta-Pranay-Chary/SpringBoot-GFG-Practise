package com.example.testserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static Logger logger= LoggerFactory.getLogger(TestController.class);
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String Name)
    {
        return "Hello "+Name+"!";
    }

    @GetMapping("/")
    public String MainMenu()
    {
        return "Hi Bro";
    }

    @GetMapping("/log-test")
    public String Log(){
        logger.error("Error Log");
        logger.warn("Warn Log");
        logger.info("Info Log");
        logger.debug("Debug Log");
        logger.trace("Trace Log");
        return "We are Testing the logs";
    }


}
