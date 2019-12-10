package com.minkov.heroes.scheduledJobs;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
public interface ScheduledJob {
    @Async
    void scheduledJob();
}
