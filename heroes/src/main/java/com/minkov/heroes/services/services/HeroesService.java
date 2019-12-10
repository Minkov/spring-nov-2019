package com.minkov.heroes.services.services;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;

import java.util.List;
import java.util.Optional;

public interface HeroesService {
    HeroDetailsServiceModel getByName(String name);

    Hero create(HeroCreateServiceModel serviceModel);

    boolean areThereOpponents();

    List<HeroDetailsServiceModel> getOpponents(String heroName);

    String getWinner(HeroDetailsServiceModel player1, HeroDetailsServiceModel player2);

    void levelUp(Hero winner);

    HeroDetailsServiceModel getByUsername(String username);

    void levelUpHeroes();
}
