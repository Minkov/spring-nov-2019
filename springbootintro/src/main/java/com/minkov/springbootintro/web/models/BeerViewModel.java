package com.minkov.springbootintro.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeerViewModel {
    private long id;
    private String name;
}
