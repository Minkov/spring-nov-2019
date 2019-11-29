package com.minkov.heroes.data.repositories;

import com.minkov.heroes.data.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HeroesRepository extends JpaRepository<Hero, Long> {

    Optional<Hero> getByNameIgnoreCase(String name);

    Optional<Hero> getByUserUsername(String username);
}
