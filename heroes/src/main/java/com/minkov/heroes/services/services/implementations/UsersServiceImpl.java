package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.HeroesRepository;
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
    private final HeroesRepository heroesRepository;
    private final ModelMapper mapper;

    public UsersServiceImpl(HeroesService heroesService, UsersRepository usersRepository, HeroesRepository heroesRepository, ModelMapper mapper) {
        this.heroesService = heroesService;
        this.usersRepository = usersRepository;
        this.heroesRepository = heroesRepository;
        this.mapper = mapper;
    }

    @Override
    public void createHeroForUser(String username, HeroCreateServiceModel heroServiceModel) throws Exception {
        User user = usersRepository.findByUsername(username);
        if(user.getHero() != null) {
            throw new Exception("User already has a hero");
        }

        Hero hero = heroesService.create(heroServiceModel);
        user.setHero(hero);

        usersRepository.saveAndFlush(user);
    }
}
