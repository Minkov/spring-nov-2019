package com.minkov.heroes.services.services.validation.base;

import com.minkov.heroes.data.models.Slot;
import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.services.validation.ItemsValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemsValidationServiceImplTest {
    ItemsValidationService service;

    @BeforeEach
    void setupTest() {
        service = new ItemsValidationServiceImpl();
    }

    @Test
    void isValid_whenNameIsNull_shouldReturnFalse() {
        ItemCreateServiceModel serviceModel = new ItemCreateServiceModel(null, Slot.PADS, 1, 2, 3, 4);
        boolean isValid = service.isValid(serviceModel);
        assertFalse(isValid);
    }

    @Test
    void isValid_whenSlotIsNull_shouldReturnFalse() {
        ItemCreateServiceModel serviceModel = new ItemCreateServiceModel("Valid name", null, 1, 2, 3, 4);
        boolean isValid = service.isValid(serviceModel);
        assertFalse(isValid);
    }

    @Test
    void isValid_whenAttackIsNegative_shouldReturnFalse() {
        ItemCreateServiceModel serviceModel = new ItemCreateServiceModel("Valid name", Slot.PADS, -1, 2, 3, 4);
        boolean isValid = service.isValid(serviceModel);
        assertFalse(isValid);
    }

    @Test
    void isValid_whenItemIsValid_shouldReturnTrue() {
        ItemCreateServiceModel serviceModel = new ItemCreateServiceModel("Valid name", Slot.PADS, 1, 2, 3, 4);
        boolean isValid = service.isValid(serviceModel);
        assertTrue(isValid);
    }
}