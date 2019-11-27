package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.models.items.ItemServiceModel;

import java.util.List;

public interface ItemsService {
    List<ItemServiceModel> getItemsForUser(String username);

    void addToUserById(long id, String username);

    void create(ItemCreateServiceModel serviceModel);
}
