package com.minkov.heroes.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/heroes")
public class HeroesController {
    @GetMapping("/details/{name}")
    public String getHeroDetails(@PathVariable String name){
        return "heroes/hero-details.html";
    }

    @GetMapping("/create")
    public String getCreateHeroForm() {
        return "heroes/create-hero.html";
    }
}
