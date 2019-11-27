package com.minkov.heroes.services.services.validation;

import com.minkov.heroes.services.models.items.ItemCreateServiceModel;

public interface ItemsValidationService {
    boolean isValid(ItemCreateServiceModel serviceModel);
}
