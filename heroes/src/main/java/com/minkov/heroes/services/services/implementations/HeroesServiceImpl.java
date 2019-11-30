package com.minkov.heroes.services.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.models.Slot;
import com.minkov.heroes.errors.HeroNotFoundException;
import com.minkov.heroes.services.factories.HeroesFactory;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.models.heroes.HeroItemServiceModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.services.models.heroes.HeroDetailsServiceModel;
import com.minkov.heroes.services.services.HeroesService;

@Service
@AllArgsConstructor
public class HeroesServiceImpl implements HeroesService {
    private final HeroesRepository heroesRepository;
    private final HeroesFactory heroesFactory;
    private final ModelMapper mapper;

    @Override
    public HeroDetailsServiceModel getByName(String name) {
        Hero hero = heroesRepository
                .getByNameIgnoreCase(name)
                .orElseThrow(() -> new HeroNotFoundException("Hero with such a name does not exist"));

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

    @Override
    public boolean areThereOpponents() {
        return heroesRepository.count() > 1;
    }

    @Override
    public List<HeroDetailsServiceModel> getOpponents(String heroName) {
        return heroesRepository.findAll()
                .stream()
                .filter(hero -> !hero.getName().equals(heroName))
                .map(hero -> mapper.map(hero, HeroDetailsServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getWinner(HeroDetailsServiceModel player1
            , HeroDetailsServiceModel player2) {

        int player1Dmg = player1.getAttack() + player1.getStrength() * 4 -
                (player2.getDefence() + player2.getStamina() * 2);
        int player2Dmg = player2.getAttack() + player2.getStrength() * 4 -
                (player1.getDefence() + player1.getStamina() * 2);

        if (player1Dmg > player2Dmg) {
            levelUp(heroesRepository
                    .getByNameIgnoreCase(player1.getName())
                    .orElseThrow(() -> new HeroNotFoundException("Not such hero")));
            return player1.getName();
        } else {
            levelUp(heroesRepository
                    .getByNameIgnoreCase(player2.getName())
                    .orElseThrow(() -> new HeroNotFoundException("Not such hero")));
            return player2.getName();
        }
    }

    @Override
    public void levelUp(Hero winner) {
        winner.setLevel(winner.getLevel() + 1);
        winner.setStamina(winner.getStamina() + 5);
        winner.setStrength(winner.getStrength() + 5);

        heroesRepository.save(winner);
    }

    private HeroItemServiceModel getItemBySlot(List<Item> items, Slot slot) {
        return items
                .stream()
                .filter(x -> x.getSlot() == slot)
                .findFirst()
                .map(item -> mapper.map(item, HeroItemServiceModel.class))
                .orElse(null);
    }
}
