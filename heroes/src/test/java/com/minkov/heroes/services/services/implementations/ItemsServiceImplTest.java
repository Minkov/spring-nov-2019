package com.minkov.heroes.services.services.implementations;

import com.minkov.heroes.data.models.Hero;
import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.models.Slot;
import com.minkov.heroes.data.models.User;
import com.minkov.heroes.data.repositories.HeroesRepository;
import com.minkov.heroes.data.repositories.ItemsRepository;
import com.minkov.heroes.services.models.heroes.HeroCreateServiceModel;
import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.models.items.ItemServiceModel;
import com.minkov.heroes.services.services.validation.ItemsValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ItemsServiceImplTest {
    List<Item> items;

    ItemsRepository itemsRepository;
    HeroesRepository heroesRepository;
    ItemsValidationService itemsValidationService;

    ItemsServiceImpl service;

    @BeforeEach
    void setupTest() {
        items = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();

        itemsRepository = Mockito.mock(ItemsRepository.class);
        heroesRepository = Mockito.mock(HeroesRepository.class);
        itemsValidationService = Mockito.mock(ItemsValidationService.class);

        Mockito.when(itemsRepository.findAll())
                .thenReturn(items);

        service = new ItemsServiceImpl(itemsRepository, heroesRepository, itemsValidationService, mapper);
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

    @Test
    void addToUserById_whenItemDoesNotExist_shouldThrowException() {
        items.clear();
        String username = "username";
        Mockito.when(itemsRepository.findById(1L))
                .thenReturn(Optional.of(new Item()));

        assertThrows(
                NullPointerException.class,
                () -> service.addToUserById(1, username));

    }

    @Test
    void addToUserById_whenUserHasNoHero_shouldThrowException() {
        items.clear();
        items.addAll(getItems());

        String username = "username";
        Mockito.when(heroesRepository.getByUserUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(
                NullPointerException.class,
                () -> service.addToUserById(items.get(0).getId(), username));
    }

    @Test
    void addToUserById_whenUserHasHeroAndItemExists_shouldBeSaved() {
        items.clear();
        items.addAll(getItems());

        String username = "username";
        Hero hero = new Hero();
        hero.setItems(new ArrayList<>());
        Mockito.when(heroesRepository.getByUserUsername(username))
                .thenReturn(Optional.of(hero));

        Mockito.when(itemsRepository.findById(1L))
                .thenReturn(Optional.of(items.get(0)));

        service.addToUserById(items.get(0).getId(), username);

        ArgumentCaptor<Hero> argument = ArgumentCaptor.forClass(Hero.class);
        Mockito.verify(heroesRepository).saveAndFlush(argument.capture());

        Hero theHero = argument.getValue();
        assertEquals(1, theHero.getItems().size());
        assertEquals(items.get(0).getId(), theHero.getItems().get(0).getId());
    }

    @Test
    void create_whenItemIsValid_shouldThrow() {
        ItemCreateServiceModel item = getItemCreateModel();

        Mockito.when(itemsValidationService.isValid(item))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> service.create(item));
    }

    @Test
    void create_whenAllIsValid_shouldThrow() {
        ItemCreateServiceModel itemModel = getItemCreateModel();

        Mockito.when(itemsValidationService.isValid(itemModel))
                .thenReturn(true);

        service.create(itemModel);


        ArgumentCaptor<Item> argument = ArgumentCaptor.forClass(Item.class);
        Mockito.verify(itemsRepository).save(argument.capture());

        Item item = argument.getValue();
        assertNotNull(item);
    }

    private ItemCreateServiceModel getItemCreateModel() {
        return new ItemCreateServiceModel("Item 1", Slot.PADS, 1, 2, 3, 4);
    }

    private List<Item> getItems() {
        return getItems(2);
    }

    private List<Item> getItems(int count) {
        return IntStream.range(0, count)
                .map(x -> x + 1)
                .mapToObj(id -> {
                    Item item = new Item();
                    item.setId(id);
                    item.setName("Item " + id);
                    item.setSlot(Slot.PADS);
                    item.setStamina(1 * id);
                    item.setAttack(2 * id);
                    item.setDefence(3 * id);
                    item.setStrength(5 * id);
                    return item;
                })
                .collect(Collectors.toList());
    }
}