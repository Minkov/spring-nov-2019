package com.minkov.heroes.services.services;

import com.minkov.heroes.base.TestBase;
import com.minkov.heroes.data.models.Gender;
import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.UsersRepository;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsersServiceTest extends TestBase {
    @MockBean
    UsersRepository usersRepository;

    @Autowired
    UsersService service;

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