package com.example.demo.services;

import java.text.NumberFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LeakService {
    private Queue<byte[]> queue = new ConcurrentLinkedQueue<byte[]>();
    Runtime runtime = Runtime.getRuntime();
    NumberFormat format = NumberFormat.getInstance();

    @Job(name="Memory leak job")
    public void executeMemoryLeakJob(int interval, int size) {
        log.info("Execute memory leak job");
        while (true) {
            log.info("Add {} bytes to the queue.", size);
            long maxMemory = runtime.maxMemory();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            log.info("max memory: {}", format.format(maxMemory / 1024));
            log.info("total memory: {}", format.format(totalMemory / 1024));
            log.info("free memory: {}", format.format(freeMemory / 1024));
            log.info("total free memory: {}", format.format((freeMemory + (maxMemory - totalMemory)) / 1024));
            queue.add(new byte[size]);
            try {
                Thread.sleep(interval * 1000);
                
            } catch (InterruptedException ex) {
                log.error("interrupted", ex);
                break;
            }
        }
    }
}
