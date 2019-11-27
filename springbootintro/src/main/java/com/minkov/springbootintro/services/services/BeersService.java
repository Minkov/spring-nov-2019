package com.minkov.springbootintro.services.services;

import com.minkov.springbootintro.services.models.BeerServiceModel;

import java.util.List;

public interface BeersService {
    List<BeerServiceModel> getBeers();

    void createBeer(String name);
}
