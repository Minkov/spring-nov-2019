package com.minkov.heroes.web.view.controllers;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.services.models.auth.LoginUserServiceModel;
import com.minkov.heroes.services.models.auth.RegisterUserServiceModel;
import com.minkov.heroes.services.services.AuthService;
import com.minkov.heroes.web.view.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper mapper;
    private final HeroesRepository heroesRepository;

    public AuthController(
            AuthService authService,
            ModelMapper mapper, HeroesRepository heroesRepository) {
        this.authService = authService;
        this.mapper = mapper;
        this.heroesRepository = heroesRepository;
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "auth/login.html";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("model", new RegisterUserModel());
        return "auth/register.html";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterUserModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register.html";
        }
        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);
        authService.register(serviceModel);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute RegisterUserModel model, HttpSession session) {
        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);
        try {
            LoginUserServiceModel loginUserServiceModel = authService.login(serviceModel);
            Optional<Hero> hero = heroesRepository
                    .getByUserUsername(loginUserServiceModel.getUsername());
            hero.ifPresent(value -> loginUserServiceModel.setHeroName(value.getName()));
            session.setAttribute("user", loginUserServiceModel);
            return "redirect:/home";
        } catch (Exception ex) {
            return "redirect:/users/login";
        }
    }
}

