package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.models.Slot;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.ItemsRepository;
import com.minkov.heroes.services.models.ItemServiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ItemsServiceImplTest {
    List<Item> items;

    ItemsRepository itemsRepository;

    ItemsServiceImpl service;

    @BeforeEach
    void setupTest() {
        items = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();

        itemsRepository = Mockito.mock(ItemsRepository.class);
        Mockito.when(itemsRepository.findAll())
                .thenReturn(items);

        service = new ItemsServiceImpl(itemsRepository, mapper);
    }

    @Test
    void getItemsForUser_whenNoItems_shouldReturnEmptyList() {
        items.clear();

        List<ItemServiceModel> actualItems = service.getItemsForUser("username");

        assertEquals(0, actualItems.size());
    }

    @Test
    void getItemsForUser_whenItemsButNotForUser_shouldReturnItemsWithoutOwned() {
        items.clear();
        items.addAll(getItems());

        List<ItemServiceModel> actualItems = service.getItemsForUser("username");

        assertEquals(items.size(), actualItems.size());
        boolean hasNoOwned = actualItems.stream()
                .filter(ItemServiceModel::isOwned)
                .findAny()
                .isEmpty();

        assertTrue(hasNoOwned);
    }

    @Test
    void getItemsForUser_whenItemsAndOneForUser_shouldReturnItemsWithOneForUser() {
        items.clear();
        items.addAll(getItems());

        Hero hero = new Hero();
        User user = new User();
        user.setUsername("username");
        hero.setUser(user);

        items.get(0)
                .setHeroes(new ArrayList<>());
        items.get(0)
                .getHeroes()
                .add(hero);

        List<ItemServiceModel> actualItems = service.getItemsForUser(user.getUsername());

        assertEquals(items.size(), actualItems.size());

        List<ItemServiceModel> itemsForUser = actualItems.stream()
                .filter(ItemServiceModel::isOwned)
                .collect(Collectors.toList());

        assertEquals(1, itemsForUser.size());
        assertEquals(items.get(0).getId(), itemsForUser.get(0).getId());
    }

    private List<Item> getItems() {
        return List.of(
                new Item() {{
                    setId(1);
                    setName("Item 1");
                    setSlot(Slot.PADS);
                    setStamina(1);
                    setAttack(2);
                    setDefence(3);
                    setStrength(5);
                }},

                new Item() {{
                    setId(2);
                    setName("Item 2");
                    setSlot(Slot.PADS);
                    setStamina(1);
                    setAttack(2);
                    setDefence(3);
                    setStrength(5);
                }}
        );
    }
}