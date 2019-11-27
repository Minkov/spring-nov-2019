package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.HeroCreateServiceModel;
import com.minkov.heroes.services.models.HeroDetailsServiceModel;

public interface UsersService {
    void createHeroForUser(String username, HeroCreateServiceModel hero) throws Exception;
}
