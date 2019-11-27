package com.minkov.heroes.services.factories.base;

import com.minkov.heroes.config.annotations.Factory;
import org.springframework.stereotype.Component;

import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.factories.HeroesFactory;

@Factory
public class HeroesFactoryImpl implements HeroesFactory {
    @Override
    public Hero create(String name, Gender gender) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setGender(gender);
        hero.setAttack(1);
        hero.setDefence(1);
        hero.setLevel(1);
        hero.setStamina(1);
        hero.setStrength(1);
        return hero;
    }
}
