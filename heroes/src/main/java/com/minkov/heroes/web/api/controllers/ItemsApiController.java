package com.minkov.heroes.web.api.controllers;

import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.services.ItemsService;
import com.minkov.heroes.web.api.models.ItemCreateRequestModel;
import com.minkov.heroes.web.api.models.ItemResponseModel;

import com.minkov.heroes.web.base.BaseController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ItemsApiController extends BaseController {
    private final ItemsService itemsService;
    private final ModelMapper mapper;

    @GetMapping(value = "/api/items")
    public ResponseEntity<List<ItemResponseModel>> getItems(HttpSession session) {
        String username = getUsername(session);
        List<ItemResponseModel> result = itemsService.getItemsForUser(username)
                .stream()
                .map(item -> mapper.map(item, ItemResponseModel.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/api/items-all")
    public List<ItemResponseModel> getItems() throws InterruptedException {
        return itemsService.getAll()
                .stream()
                .map(item -> mapper.map(item, ItemResponseModel.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/items/add-to-user/{id}")
    public void buyItem(@PathVariable long id, HttpSession session) {
        String username = getUsername(session);
        itemsService.addToUserById(id, username);
    }

    @PostMapping("/api/items")
    public ResponseEntity<Void> create(ItemCreateRequestModel requestModel) {
        ItemCreateServiceModel serviceModel = mapper.map(requestModel, ItemCreateServiceModel.class);
        itemsService.create(serviceModel);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
