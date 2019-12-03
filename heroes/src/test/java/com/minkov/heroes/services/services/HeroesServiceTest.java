package com.minkov.heroes.services.services;

import com.minkov.heroes.base.TestBase;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.factories.HeroesFactory;
import com.minkov.heroes.services.factories.base.HeroesFactoryImpl;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.services.services.validation.ItemsValidationService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HeroesServiceTest extends TestBase {
    @MockBean
    HeroesRepository heroesRepository;

    @Autowired
    HeroesService service;

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

    @Test
    void levelUp_whenHeroWon_shouldReturnCorrectLevel() {
        Hero hero = new Hero();
        hero.setName("Pesho");

        service.levelUp(hero);
        assertEquals(hero.getLevel(), 1);
    }
}