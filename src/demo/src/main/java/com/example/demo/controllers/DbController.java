package com.example.demo.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("db")
public class DbController {

    @Autowired
    DataSource ds;
    
    private String process(Integer millis) {
        try (final Connection conn = ds.getConnection();
             final PreparedStatement statement = conn.prepareStatement("SELECT 1")) {
                log.info("Qyery: " + conn.toString());
                statement.executeQuery();
                log.info("Sleep: {} sec", millis);
                Thread.sleep(millis);
                log.info("Done");
                conn.close();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Err");
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error(e.getSQLState());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Err");
        }
        return "Ok";
    }

    @RequestMapping(path="/{millis}", method=RequestMethod.GET)
    public String get(@PathVariable Integer millis) {
        log.info("db GET: {}", millis);
        return process(millis);
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String get() {
        log.info("db GET");
        return process(0);
    }
}
