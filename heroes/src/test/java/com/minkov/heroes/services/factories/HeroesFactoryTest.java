package com.minkov.heroes.services.factories;

import com.minkov.heroes.base.TestBase;
import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.services.base.ServiceTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static com.minkov.heroes.services.factories.HeroesConstants.*;

class HeroesFactoryTest extends ServiceTestBase {
    @Autowired
    HeroesFactory factory;

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