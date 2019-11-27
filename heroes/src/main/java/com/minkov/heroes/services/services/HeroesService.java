package com.minkov.heroes.services.services;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.models.HeroCreateServiceModel;
import com.minkov.heroes.services.models.HeroDetailsServiceModel;

public interface HeroesService {
    HeroDetailsServiceModel getByName(String name);

    Hero create(HeroCreateServiceModel serviceModel);
}
