package com.example.demo;

import java.time.Duration;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.services.LeakService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {
	static private int DefaultIntervalSec = 5;
	static private int DefaultLeakSize = 1024*1024*10;

	@Autowired
	private JobScheduler jobScheduler;

	@Value("${app.job.leak.enabled:false}")
    private boolean enableLeakJob;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void scheduleRecurrently() {
		if (enableLeakJob) {
			jobScheduler.<LeakService>scheduleRecurrently(Duration.ofSeconds(DefaultIntervalSec), x -> x.executeMemoryLeakJob(DefaultLeakSize));
		}
	}
}
