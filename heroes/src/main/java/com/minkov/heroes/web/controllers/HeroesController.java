package com.minkov.heroes.web.controllers;

import com.minkov.heroes.services.models.HeroDetailsServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.web.models.HeroDetailsViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/heroes")
public class HeroesController {
    private final HeroesService heroesService;
    private final ModelMapper mapper;

    public HeroesController(HeroesService heroesService, ModelMapper mapper) {
        this.heroesService = heroesService;
        this.mapper = mapper;
    }

    @GetMapping("/details/{name}")
    public ModelAndView getHeroDetails(@PathVariable String name, ModelAndView modelAndView) {
        HeroDetailsServiceModel serviceModel = heroesService.getByName(name);
        HeroDetailsViewModel viewModel = mapper.map(serviceModel, HeroDetailsViewModel.class);
        modelAndView.addObject("hero", viewModel);
        modelAndView.setViewName("heroes/hero-details.html");
        return modelAndView;
    }

    @GetMapping("/create")
    public String getCreateHeroForm() {
        return "heroes/create-hero.html";
    }
}
