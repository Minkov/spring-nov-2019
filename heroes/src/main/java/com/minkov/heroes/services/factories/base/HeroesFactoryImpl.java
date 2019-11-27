package com.minkov.heroes.services.factories.base;

import com.minkov.heroes.config.annotations.Factory;

import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.factories.HeroesFactory;

import static com.minkov.heroes.services.factories.HeroesConstants.*;

@Factory
public class HeroesFactoryImpl implements HeroesFactory {
    @Override
    public Hero create(String name, Gender gender) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setGender(gender);
        hero.setAttack(INITIAL_ATTACK);
        hero.setDefence(INITIAL_DEFENCE);
        hero.setLevel(INITIAL_LEVEL);
        hero.setStamina(INITIAL_STAMINA);
        hero.setStrength(INITIAL_STRENGTH);

        return hero;
    }
}
