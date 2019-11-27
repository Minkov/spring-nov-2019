package com.minkov.heroes.services.services;

import com.minkov.heroes.services.models.ItemServiceModel;

import java.util.List;

public interface ItemsService {
    List<ItemServiceModel> getItemsForUser(String username);

    void createForUserById(long id, String username);
}
