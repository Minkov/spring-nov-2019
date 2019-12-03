package com.minkov.heroes.services.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.services.validation.ItemsValidationService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.data.repositories.ItemsRepository;
import com.minkov.heroes.services.models.items.ItemServiceModel;
import com.minkov.heroes.services.services.ItemsService;

@Service
@AllArgsConstructor
public class ItemsServiceImpl implements ItemsService {
    private final ItemsRepository itemsRepository;
    private final HeroesRepository heroesRepository;
    private final ItemsValidationService itemsValidationService;
    private final ModelMapper mapper;

    @Override
    public List<ItemServiceModel> getItemsForUser(String username) {
        return itemsRepository.findAll()
                .stream()
                .map(item -> {
                    ItemServiceModel serviceModel = mapper.map(item, ItemServiceModel.class);
                    if (item.getHeroes() != null) {
                        Hero hero = item.getHeroes()
                                .stream()
                                .filter(h -> h.getUser().getUsername().equals(username))
                                .findAny()
                                .orElse(null);

                        serviceModel.setOwned(hero != null);
                    }
                    return serviceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addToUserById(long id, String username) {
        Optional<Hero> heroResult = heroesRepository.getByUserUsername(username);
        if (heroResult.isEmpty()) {
            throw new NullPointerException("User does not have a hero");
        }

        Optional<Item> itemResult = itemsRepository.findById(id);
        if (itemResult.isEmpty()) {
            throw new NullPointerException("Item does not exists");
        }

        Hero hero = heroResult.get();
        Item item = itemResult.get();

        boolean hasItem = false;
        for (Item currItem: hero.getItems() ) {
            if (currItem.getSlot() == item.getSlot()) {
                hasItem = true;
                break;
            }
        }

        if (!hasItem) {
            hero.getItems().add(item);
            hero.setStrength(hero.getStrength() + item.getStrength());
            hero.setStamina(hero.getStamina() + item.getStamina());
            hero.setAttack(hero.getAttack() + item.getAttack());
            hero.setDefence(hero.getDefence() + item.getDefence());

            heroesRepository.saveAndFlush(hero);
        }

    }

    @Override
    public void create(ItemCreateServiceModel serviceModel) {
        if (!this.itemsValidationService.isValid(serviceModel)) {
           throw new RuntimeException("Hero is invalid");
        }

        Item item = mapper.map(serviceModel, Item.class);
        itemsRepository.save(item);
    }

    @Override
    public List<ItemServiceModel> getAll() {
        return itemsRepository.findAll()
                .stream()
                .map(item -> mapper.map(item, ItemServiceModel.class))
                .collect(Collectors.toList());
    }
}
