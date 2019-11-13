package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.HeroDetailsServiceModel;

public interface HeroesService {
    HeroDetailsServiceModel getByName(String name);
}
