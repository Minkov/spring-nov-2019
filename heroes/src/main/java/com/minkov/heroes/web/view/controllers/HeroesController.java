package com.minkov.heroes.web.view.controllers;

import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;
import com.minkov.heroes.services.models.auth.LoginUserServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.services.services.UsersService;
import com.minkov.heroes.web.base.BaseController;
import com.minkov.heroes.web.view.models.HeroCreateModel;
import com.minkov.heroes.web.view.models.HeroDetailsViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/heroes")
@AllArgsConstructor
public class HeroesController extends BaseController {
    public final static String HEROES_HERO_DETAILS_VIEW_NAME = "heroes/hero-details.html";

    private final HeroesService heroesService;
    private final ModelMapper mapper;
    private final UsersService usersService;

    @GetMapping("/details/{name}")
    public ModelAndView getHeroDetails(@PathVariable String name, ModelAndView modelAndView) {
        HeroDetailsServiceModel serviceModel = heroesService.getByName(name);
        HeroDetailsViewModel viewModel = mapper.map(serviceModel, HeroDetailsViewModel.class);
        modelAndView.addObject("hero", viewModel);
        modelAndView.setViewName(HEROES_HERO_DETAILS_VIEW_NAME);
        return modelAndView;
    }

    @GetMapping("/create")
    public String getCreateHeroForm(HttpSession session) {
        return "heroes/create-hero.html";
    }

    @PostMapping("/create")
    public String createHero(@ModelAttribute HeroCreateModel hero, Principal principal) {
        String username = principal.getName();

        HeroCreateServiceModel serviceModel = mapper.map(hero, HeroCreateServiceModel.class);
        try {
            usersService.createHeroForUser(username, serviceModel);
            return "redirect:/heroes/details/" + hero.getName();
        } catch (Exception ex) {
            return "redirect:/heroes/create";
        }
    }

    @GetMapping("/fight/{heroName}")
    public ModelAndView fight(@PathVariable String heroName, ModelAndView modelAndView ,HttpSession session) {
        modelAndView.setViewName("heroes/fight");

        HeroDetailsServiceModel currentHero = heroesService.getByName(getHeroName(session));
        HeroDetailsServiceModel opponent = heroesService.getByName(heroName);

        String winner = heroesService.getWinner(currentHero, opponent);

        modelAndView.addObject("currentHero", currentHero);
        modelAndView.addObject("opponent", opponent);
        modelAndView.addObject("winner", winner);
        return modelAndView;
    }

    @ExceptionHandler(HeroNotFoundException.class)
    public ModelAndView handleException(HeroNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }
}
