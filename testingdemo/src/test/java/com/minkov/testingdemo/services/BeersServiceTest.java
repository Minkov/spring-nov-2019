package com.minkov.testingdemo.services;

import com.minkov.testingdemo.Beer;
import com.minkov.testingdemo.repositories.BeersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeersServiceTest {
    BeersRepository beersRepository;

    BeersService beersService;

    List<Beer> beers;

    @BeforeEach
    public void setupTest() {
        beers = new ArrayList<>();
        beersRepository = Mockito.mock(BeersRepository.class);

        Mockito.when(beersRepository.getAllBeers())
                .thenReturn(beers);

        beersService = new BeersService(beersRepository);
    }

    @Test
    void getAllBeers_whenTwoUnorderedBeers_shouldReturnThemSorted() {
        beers.add(new Beer(1, "Zagorka"));
        beers.add(new Beer(2, "Kamentza"));

        // ^ arrange

        // act
        List<Beer> actualBeers = beersService.getAllBeers();

        // assert
        assertEquals(beers.size(), actualBeers.size(), "Count does not match");
        assertEquals(beers.get(1).getName(), actualBeers.get(0).getName());
        assertEquals(beers.get(0).getName(), actualBeers.get(1).getName());
    }
}