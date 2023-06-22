package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("readiness")
public class ReadinessController {
    private long count = 0;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<String> get() {
        log.info("readiness GET: count = {}", count);
        if (count == 0) {
            return new ResponseEntity<String>("Ok", HttpStatus.OK);
        }
        if (count > 0) {
            count -= 1;
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping(value="/{newCount}", method=RequestMethod.POST)
    public String post(@PathVariable Long newCount) {
        log.info("readiness POST: {}", newCount);
        count = newCount;
        return "Ok";
    }
}
