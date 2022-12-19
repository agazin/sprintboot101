package com.axonstech.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping
    public String hello(){
        return helloService.hello();
    }
}
