package ua.univ.vsynytsyn.timetable.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping()
    String hello(){
        return "<h1>Hello world</h1>";
    }
}
