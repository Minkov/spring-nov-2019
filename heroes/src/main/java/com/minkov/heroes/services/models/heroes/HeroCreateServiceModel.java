package com.minkov.heroes.services.models.heroes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.minkov.heroes.data.models.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeroCreateServiceModel {
    private String name;
    private Gender gender;
}
