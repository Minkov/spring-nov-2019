package com.minkov.heroes.services.models.heroes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeroItemServiceModel {
    private String name;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
}
