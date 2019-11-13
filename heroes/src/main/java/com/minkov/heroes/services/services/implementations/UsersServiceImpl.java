package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.UsersRepository;
import com.minkov.heroes.services.models.HeroCreateServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.services.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final HeroesService heroesService;
    private final UsersRepository usersRepository;
    private final ModelMapper mapper;

    public UsersServiceImpl(HeroesService heroesService, UsersRepository usersRepository, ModelMapper mapper) {
        this.heroesService = heroesService;
        this.usersRepository = usersRepository;
        this.mapper = mapper;
    }

    @Override
    public void createHeroForUser(String username, HeroCreateServiceModel heroServiceModel) {
        User user = usersRepository.findByUsername(username);
        Hero hero = heroesService.create(heroServiceModel);
        user.setHero(hero);
        usersRepository.save(user);
    }
}
