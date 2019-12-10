package com.minkov.heroes.web.view.controllers;

import com.minkov.heroes.services.services.AuthenticatedUserService;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.web.base.BaseController;
import com.minkov.heroes.web.view.models.HeroHomeModel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final HeroesService heroesService;
    private final ModelMapper modelMapper;
    private final AuthenticatedUserService authenticatedUserService;

    public HomeController(
            HeroesService heroesService,
            ModelMapper modelMapper,
            AuthenticatedUserService authenticatedUserService) {
        this.heroesService = heroesService;
        this.modelMapper = modelMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/")
    public String getIndex(HttpSession session) {
        return "home/index.html";
    }

    @GetMapping("/home")
    public ModelAndView getHome(ModelAndView modelAndView) {
        List<String> authorities = authenticatedUserService.getRoles();
        modelAndView.setViewName("home/home");

        boolean condition = heroesService.areThereOpponents();

        ////                .getOpponents(getHeroName(session)))
////        List<HeroHomeModel> heroes = heroesService
////                .getOpponents(getHeroName(session))
////                    .stream()
////                    .map(hero -> modelMapper.map(hero, HeroHomeModel.class))
////                    .collect(Collectors.toList());
////        modelAndView.addObject("condition", condition);
////        modelAndView.addObject("heroes", heroes);

        return modelAndView;
    }
}
