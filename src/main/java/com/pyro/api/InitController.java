package com.pyro.api;

import org.springframework.web.bind.annotation.*;


@RestController
public class InitController {



    @GetMapping({"/", "/help", "/info"})
    String info() {
        return "Hello";
    }

}
