package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.UsersRepository;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.services.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final HeroesService heroesService;
    private final UsersRepository usersRepository;

    @Override
    public void createHeroForUser(String username, HeroCreateServiceModel heroServiceModel) throws Exception {
        User user = usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getHero() != null) {
            throw new Exception("User already has a hero");
        }

        Hero hero = heroesService.create(heroServiceModel);
        hero.setUser(user);
        user.setHero(hero);

        usersRepository.save(user);
    }
}
