package com.minkov.springbootintro.data.repositories;

import com.minkov.springbootintro.data.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeersRepository extends JpaRepository<Beer, Long> {
}
