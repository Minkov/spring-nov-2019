package com.minkov.heroes.web.filters;

import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;
import com.minkov.heroes.services.services.AuthenticatedUserService;
import com.minkov.heroes.services.services.HeroesService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class UserHeroInterceptor implements HandlerInterceptor {
    private final AuthenticatedUserService authenticatedUserService;
    private final HeroesService heroesService;

    public UserHeroInterceptor(
            AuthenticatedUserService authenticatedUserService,
            HeroesService heroesService) {

        this.authenticatedUserService = authenticatedUserService;
        this.heroesService = heroesService;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String username = authenticatedUserService.getUsername();
        try {
            HeroDetailsServiceModel hero = heroesService.getByUsername(username);
            request.getSession()
                    .setAttribute("heroName", hero.getName());
        } catch (HeroNotFoundException ex) {
            // do nothing
        }
    }
}
