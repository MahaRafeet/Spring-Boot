package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

@RestController


public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "Guest") String name) {
        return "Hello " + name ;
    }


    @GetMapping("/sum")
    public Integer sum(@RequestParam Integer a ,@RequestParam Integer b) {
        return a + b ;
    }

    @GetMapping("/info")
    public HashMap<String, String> info(@RequestParam String name , @RequestParam String city , @RequestParam String language) {

        HashMap<String, String> infoMap = new HashMap<>() ;
        infoMap.put("name" , name) ;
        infoMap.put("city" , city) ;
        infoMap.put("language" , language) ;
        return infoMap ;

    }
    @GetMapping("/greet")
    public String greeting(@RequestParam String name) {
        return "Welcome " + name + " to our site!" ;
    }

    @GetMapping("/upper")
    public String upper(@RequestParam String text) {
        return text.toUpperCase() ;
    }

    @GetMapping("/random")
    public Integer random(@RequestParam Integer min , @RequestParam Integer max) {
        Random random=new Random();
        return random.nextInt(max-min+1) + min ;
    }










}
