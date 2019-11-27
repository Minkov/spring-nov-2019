package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;

public interface UsersService {
    void createHeroForUser(String username, HeroCreateServiceModel hero) throws Exception;
}
