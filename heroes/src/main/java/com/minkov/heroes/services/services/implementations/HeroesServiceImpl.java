package com.minkov.heroes.services.services.implementations;

import java.util.List;
import java.util.Optional;

import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.models.Slot;
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
    private final ModelMapper mapper;

    public HeroesServiceImpl(HeroesRepository heroesRepository, ModelMapper mapper) {
        this.heroesRepository = heroesRepository;
        this.mapper = mapper;
    }

    @Override
    public HeroDetailsServiceModel getByName(String name) {
        Optional<Hero> heroOptional = heroesRepository.getByNameIgnoreCase(name);
        if (heroOptional.isEmpty()) {
            throw new NullPointerException("No such hero");
        }

        Hero hero = heroOptional.get();

        HeroDetailsServiceModel serviceModel = mapper.map(hero, HeroDetailsServiceModel.class);

        serviceModel.setWeapon(getItemBySlot(hero.getItems(), Slot.WEAPON));
        serviceModel.setGauntlets(getItemBySlot(hero.getItems(), Slot.GAUNTLETS));
        serviceModel.setHelmet(getItemBySlot(hero.getItems(), Slot.HELMET));
        serviceModel.setPads(getItemBySlot(hero.getItems(), Slot.PADS));
        serviceModel.setPauldrons(getItemBySlot(hero.getItems(), Slot.PAULDRON));

        return serviceModel;
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
