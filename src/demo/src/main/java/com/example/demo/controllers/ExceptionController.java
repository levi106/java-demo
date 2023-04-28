package com.example.demo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("ex")
public class ExceptionController {

    @RequestMapping(path="/{verb}", method=RequestMethod.GET)
    public String get(@PathVariable String verb) {
        log.info("ex GET: {}", verb);
        switch (verb) {
            case "ok":
                return "Ok";
            case "err":
                throw new RuntimeException("Err");
            case "catch":
                try {
                    throw new IllegalArgumentException("catch");
                } catch (IllegalArgumentException ex) {
                    log.error("catch", ex);
                    return "Err";
                }
            default:
                throw new IllegalArgumentException("Invalid verb");
        }
    }
}
