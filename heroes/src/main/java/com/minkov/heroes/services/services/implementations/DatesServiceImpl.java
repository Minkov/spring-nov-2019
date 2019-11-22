package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.services.services.DatesService;

import java.util.Date;

public class DatesServiceImpl implements DatesService {
    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
