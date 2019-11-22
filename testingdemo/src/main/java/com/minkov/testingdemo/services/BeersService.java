package com.minkov.testingdemo.services;

import com.minkov.testingdemo.Beer;
import com.minkov.testingdemo.repositories.BeersRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BeersService {
    private final BeersRepository beersRepository;

    public BeersService(BeersRepository beersRepository) {
        this.beersRepository = beersRepository;
    }

    public void addBeer(String name) {
        // validation
        beersRepository.addBeer(name);
    }

    public List<Beer> getAllBeers() {
        return beersRepository.getAllBeers()
                .stream()
                .sorted((x, y) -> x.getName().compareTo(y.getName()))
                .collect(Collectors.toList());
    }
}
