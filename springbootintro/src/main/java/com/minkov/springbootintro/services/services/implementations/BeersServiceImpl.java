package com.minkov.springbootintro.services.services.implementations;

import com.minkov.springbootintro.data.models.Beer;
import com.minkov.springbootintro.data.repositories.BeersRepository;
import com.minkov.springbootintro.services.services.BeersService;
import com.minkov.springbootintro.services.models.BeerServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeersServiceImpl implements BeersService {
    private final ModelMapper mapper;
    private final BeersRepository beersRepository;

    public BeersServiceImpl(
            ModelMapper mapper,
            BeersRepository beersRepository) {
        this.mapper = mapper;
        this.beersRepository = beersRepository;
    }

    @Override
    public List<BeerServiceModel> getBeers() {
        return beersRepository.findAll()
                .stream()
                .map(beer -> mapper.map(beer, BeerServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createBeer(String name) {
        Beer beer = new Beer();
        beer.setName(name);
        beersRepository.saveAndFlush(beer);
    }
}
