package com.minkov.heroes.services.services.implementations;

import java.util.List;
import java.util.Optional;

import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.models.Slot;
import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.factories.HeroesFactory;
import com.minkov.heroes.services.models.HeroCreateServiceModel;
import com.minkov.heroes.services.models.HeroItemServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.services.models.HeroDetailsServiceModel;
import com.minkov.heroes.services.services.HeroesService;

@Service
public class HeroesServiceImpl implements HeroesService {
    private final HeroesRepository heroesRepository;
    private final HeroesFactory heroesFactory;
    private final ModelMapper mapper;

    public HeroesServiceImpl(HeroesRepository heroesRepository, HeroesFactory heroesFactory, ModelMapper mapper) {
        this.heroesRepository = heroesRepository;
        this.heroesFactory = heroesFactory;
        this.mapper = mapper;
    }

    @Override
    public HeroDetailsServiceModel getByName(String name) {
        Hero hero = heroesRepository.getByNameIgnoreCase(name).orElseThrow(() -> new HeroNotFoundException("Nqma takuv geroi"));

        HeroDetailsServiceModel serviceModel = mapper.map(hero, HeroDetailsServiceModel.class);

        serviceModel.setWeapon(getItemBySlot(hero.getItems(), Slot.WEAPON));
        serviceModel.setGauntlets(getItemBySlot(hero.getItems(), Slot.GAUNTLETS));
        serviceModel.setHelmet(getItemBySlot(hero.getItems(), Slot.HELMET));
        serviceModel.setPads(getItemBySlot(hero.getItems(), Slot.PADS));
        serviceModel.setPauldrons(getItemBySlot(hero.getItems(), Slot.PAULDRON));

        return serviceModel;
    }

    @Override
    public Hero create(HeroCreateServiceModel serviceModel) {
        Hero hero = heroesFactory.create(serviceModel.getName(), serviceModel.getGender());
        heroesRepository.save(hero);
        return hero;
    }

    private HeroItemServiceModel getItemBySlot(List<Item> items, Slot slot) {
        Optional<Item> item = items
                .stream()
                .filter(x -> x.getSlot() == slot)
                .findFirst();

        return item.isPresent()
                ? mapper.map(item, HeroItemServiceModel.class)
                : null;
    }
}
