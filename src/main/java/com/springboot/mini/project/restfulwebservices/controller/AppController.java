package com.springboot.mini.project.restfulwebservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping(path = "/hello-world")
    public String tellHelloWorld()
    {
        return "Hello World";
    }
}
