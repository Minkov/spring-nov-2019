package com.minkov.springbootintro.web.controllers;

import com.minkov.springbootintro.services.services.BeersService;
import com.minkov.springbootintro.web.models.BeerViewModel;
import com.minkov.springbootintro.web.models.CreateBeerViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BeersController {
    private final BeersService beersService;
    private final ModelMapper mapper;

    public BeersController(BeersService beersService, ModelMapper mapper) {
        this.beersService = beersService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ModelAndView getAllBeers(ModelAndView modelAndView) {
        modelAndView.addObject("text", "Hello, from ModelAndView");
        List<BeerViewModel> beers = beersService.getBeers()
            .stream()
            .map(beer -> mapper.map(beer, BeerViewModel.class))
            .collect(Collectors.toList());

        modelAndView.addObject("beers", beers);
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

//    @GetMapping("/{beerName}")
    public ModelAndView getBeerDetails(
            ModelAndView modelAndView,
            @PathVariable("beerName") String beerName) {
        modelAndView.addObject("beer", beerName);
        modelAndView.setViewName("details.html");
        return modelAndView;
    }

    @PostMapping("/")
    public String createBeer(@ModelAttribute CreateBeerViewModel model) {
        beersService.createBeer(model.getName());
        return "redirect:/";
    }
}
