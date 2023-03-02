package com.example.demo;

import java.time.Duration;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.services.LeakService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoApplication {
	static private int DefaultIntervalSec = 5;
	static private int DefaultLeakSize = 1024*1024*10;

	@Autowired
	private JobScheduler jobScheduler;

	@Value("${app.job.leak.enabled:false}")
    private boolean enableLeakJob;

	@Value("${app.initial.memory.allocation:0}")
	private int initialMemoryAllocation;

	private byte[] array;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void scheduleRecurrently() {
		if (enableLeakJob) {
			jobScheduler.<LeakService>scheduleRecurrently(Duration.ofSeconds(DefaultIntervalSec), x -> x.executeMemoryLeakJob(DefaultLeakSize));
		}
	}
	@PostConstruct
	public void initialMemoryAllocation() {
		if (initialMemoryAllocation > 0) {
			log.info("Allocate {} bytes", initialMemoryAllocation);
			array = new byte[initialMemoryAllocation];
		}
	}
}
