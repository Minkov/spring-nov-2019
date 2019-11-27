package com.minkov.heroes.services.services;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;

public interface HeroesService {
    HeroDetailsServiceModel getByName(String name);

    Hero create(HeroCreateServiceModel serviceModel);
}
