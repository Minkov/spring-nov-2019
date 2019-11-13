package com.minkov.heroes.web.models;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoAutoStart
public class HeroCreateModel {
    private String name;
    private String gender;
}
