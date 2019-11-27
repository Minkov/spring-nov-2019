package com.minkov.testingdemo.repositories;

import com.minkov.testingdemo.Beer;

import java.util.ArrayList;
import java.util.List;

public class BeersRepositoryImpl implements BeersRepository {
    private static List<Beer> beers;

    static {
        beers = new ArrayList<>();
    }

    @Override
    public void addBeer(String name){
        Beer beer = new Beer();
        beer.setId(1);
        beer.setName(name);
        beers.add(beer);
    }

    @Override
    public List<Beer> getAllBeers() {
        return beers;
    }
}
