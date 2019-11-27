package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.data.repositories.UsersRepository;
import com.minkov.heroes.services.factories.HeroesFactory;
import com.minkov.heroes.services.factories.base.HeroesFactoryImpl;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {
    HeroesService heroesService;
    UsersRepository usersRepository;
    HeroesRepository heroesRepository;
    HeroesFactory heroesFactory;

    UsersServiceImpl service;

    @BeforeEach
    public void setupTest() {
        usersRepository = Mockito.mock(UsersRepository.class);
        heroesRepository = Mockito.mock(HeroesRepository.class);
        heroesFactory = new HeroesFactoryImpl();
        ModelMapper mapper = new ModelMapper();
        heroesService = new HeroesServiceImpl(heroesRepository, heroesFactory, mapper);
        service = new UsersServiceImpl(heroesService, usersRepository, heroesRepository, mapper);
    }

    @Test
    public void createHeroForUser_whenUserExistsAndDoesNotHaveAHero_shouldCreateHeroForUser() throws Exception {
        User user = new User();
        user.setUsername("Pesho");
        String heroName = "Gosho";
        Mockito.when(usersRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        HeroCreateServiceModel heroToCreate = new HeroCreateServiceModel(heroName, Gender.MALE);

        service.createHeroForUser(user.getUsername(), heroToCreate);

        assertEquals(heroName, user.getHero().getName());
    }

    public void createHeroForUser_whenUserDoesNOTExist_shouldThrowException() {

    }

    @Test
    public void createHeroForUser_whenUserExistsAndHasAHero_shouldThrowException() {
        User user = new User();
        user.setUsername("Pesho");
        user.setHero(new Hero());
        String heroName = "Gosho";
        Mockito.when(usersRepository.findByUsername(user.getUsername()))
                .thenReturn(user);

        HeroCreateServiceModel heroToCreate = new HeroCreateServiceModel(heroName, Gender.MALE);

        assertThrows(Exception.class, () ->
                service.createHeroForUser(user.getUsername(), heroToCreate));
    }
}