package com.minkov.heroes.services.services.validation.base;

import com.minkov.heroes.services.models.items.ItemCreateServiceModel;
import com.minkov.heroes.services.services.validation.ItemsValidationService;
import org.springframework.stereotype.Service;

@Service
public class ItemsValidationServiceImpl implements ItemsValidationService {
    public boolean isValid(ItemCreateServiceModel serviceModel) {
        return serviceModel != null &&
                serviceModel.getName() != null &&
                serviceModel.getSlot() != null &&
                serviceModel.getAttack() > 0;
    }
}
