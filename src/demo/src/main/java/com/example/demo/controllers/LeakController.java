package com.example.demo.controllers;

import java.time.Duration;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.LeakService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("leak")
public class LeakController {
    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    private LeakService leakService;
    private JobId id = null;

    static private int DefaultIntervalSec = 5;
    static private int DefaultLeakSize = 1024*1024*10;
    
    @RequestMapping(method=RequestMethod.PUT)
    public String put() {
        log.info("leak PUT");
        if (id != null) {
            return "Already running";
        }
        id = jobScheduler.enqueue(() -> leakService.executeMemoryLeakJob(DefaultIntervalSec, DefaultLeakSize));
  
        return id.toString();
    }

    @RequestMapping(method=RequestMethod.DELETE)
    public String delete() {
        log.info("leak DELETE");

        if (id != null) {
            jobScheduler.delete(id);
            String result = id.toString();
            id = null;
            return result;
        }

        return "No job";
    }
}
