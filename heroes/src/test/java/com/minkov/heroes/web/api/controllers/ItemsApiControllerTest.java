package com.minkov.heroes.web.api.controllers;

import com.minkov.heroes.data.models.Item;
import com.minkov.heroes.data.repositories.ItemsRepository;
import com.minkov.heroes.web.api.models.ItemResponseModel;
import com.minkov.heroes.web.base.ApiTestBase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemsApiControllerTest extends ApiTestBase {
    @MockBean
    ItemsRepository itemsRepository;

    @Test
    void getAllItems_whenItems_shouldReturnItems() {
        Item item = new Item();
        item.setId(1);
        item.setName("Sword");
        Mockito.when(itemsRepository.findAll())
                .thenReturn(List.of(item));

        ResponseEntity<ItemResponseModel[]> responseEntity = getRestTemplate()
                .getForObject(
                        getFullUrl("/api/items-all"),
                        ResponseEntity.class);

        var result = responseEntity.getBody();

        assertEquals(1, result.length);
        assertEquals(item.getId(), result[0].getId());
        assertEquals(item.getName(), result[0].getName());
    }
}