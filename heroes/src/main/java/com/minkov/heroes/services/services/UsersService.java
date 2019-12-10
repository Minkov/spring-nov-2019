package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    void createHeroForUser(String username, HeroCreateServiceModel hero) throws Exception;
}
