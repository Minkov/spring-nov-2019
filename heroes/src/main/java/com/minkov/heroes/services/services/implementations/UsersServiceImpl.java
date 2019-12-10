package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.data.repositories.UsersRepository;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.services.HeroesService;
import com.minkov.heroes.services.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {
    private final HeroesService heroesService;
    private final UsersRepository usersRepository;

    public UsersServiceImpl(HeroesService heroesService, UsersRepository usersRepository) {
        this.heroesService = heroesService;
        this.usersRepository = usersRepository;
    }

    @Override
    public void createHeroForUser(String username, HeroCreateServiceModel heroServiceModel) throws Exception {
        User user = usersRepository.findByUsername(username);
        if (user.getHero() != null) {
            throw new Exception("User already has a hero");
        }

        Hero hero = heroesService.create(heroServiceModel);
        user.setHero(hero);

        usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(s);

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
                );
    }
}
