package com.minkov.heroes.services.factories.base;

import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.minkov.heroes.services.factories.HeroesConstants.*;

class HeroesFactoryImplTest {

    HeroesFactoryImpl factory;

    @BeforeEach
    void setupTest() {
        factory = new HeroesFactoryImpl();
    }

    @Test
    void create_withNameAndGender_shouldReturnHeroWithDefaultProps() {
        String name = "Hero";
        Gender gender = Gender.FEMALE;

        Hero hero = factory.create(name, gender);

        assertEquals(name, hero.getName());
        assertEquals(gender, hero.getGender());
        assertEquals(INITIAL_ATTACK, hero.getAttack());
        assertEquals(INITIAL_DEFENCE, hero.getDefence());
        assertEquals(INITIAL_STAMINA, hero.getStamina());
        assertEquals(INITIAL_STRENGTH, hero.getStrength());
    }
}