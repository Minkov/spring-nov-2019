package com.minkov.heroes.scheduledJobs.implementation;

import com.minkov.heroes.scheduledJobs.ScheduledJob;
import com.minkov.heroes.services.services.HeroesService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LevelUpHeroesSchedulerJob implements ScheduledJob {
    private final HeroesService heroesService;

    public LevelUpHeroesSchedulerJob(HeroesService heroesService) {
        this.heroesService = heroesService;
    }

    @Override
    @Scheduled(cron =  "0 0/1 * 1/1 * ?")
    public void scheduledJob() {
        heroesService.levelUpHeroes();
    }
}
