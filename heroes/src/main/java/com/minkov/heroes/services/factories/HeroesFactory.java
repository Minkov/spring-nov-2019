package com.minkov.heroes.services.factories;

import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;

public interface HeroesFactory {
    Hero create(String name, Gender gender);
}
