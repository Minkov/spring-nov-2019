package com.minkov.testingdemo.repositories;

import com.minkov.testingdemo.Beer;

import java.util.List;

public interface BeersRepository {
    void addBeer(String name);

    List<Beer> getAllBeers();
}