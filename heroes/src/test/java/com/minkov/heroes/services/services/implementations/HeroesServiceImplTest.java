package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.factories.HeroesFactory;
import com.minkov.heroes.services.factories.base.HeroesFactoryImpl;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HeroesServiceImplTest {
    HeroesRepository heroesRepository;
    HeroesFactory heroesFactory;
    ModelMapper mapper;

    HeroesServiceImpl service;

    @BeforeEach
    void setupTest() {
        heroesRepository = Mockito.mock(HeroesRepository.class);
        heroesFactory = new HeroesFactoryImpl();
        mapper = new ModelMapper();

        service = new HeroesServiceImpl(heroesRepository, heroesFactory, mapper);
    }

    @Test
    void getByName_whenHeroDoesNotExist_shouldThrowHeroNotFoundException() {
        String heroName = "Hero Name";

        Mockito.when(heroesRepository.getByNameIgnoreCase(heroName))
                .thenReturn(Optional.empty());

        assertThrows(
                HeroNotFoundException.class,
                () -> service.getByName(heroName));
    }

    @Test
    void getByName_whenHeroDoesExist_shouldReturnHero() {
        String heroName = "Hero name";

        Hero hero = new Hero();
        hero.setName(heroName);
        hero.setItems(new ArrayList<>());

        Mockito.when(heroesRepository.getByNameIgnoreCase(heroName))
                .thenReturn(Optional.of(hero));

        HeroDetailsServiceModel heroDetails = service.getByName(heroName);

        assertEquals(hero.getName(), heroDetails.getName());
    }
}