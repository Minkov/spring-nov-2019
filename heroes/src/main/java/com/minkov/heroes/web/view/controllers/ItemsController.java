package com.minkov.heroes.web.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/items")
@Controller
public class ItemsController {

    @GetMapping("/merchant")
    public String getMerchant() {
        return "items/merchant.html";
    }

    @GetMapping("/create")
    public String getCreateItemForm() {
        return "items/create-item.html";
    }

}
