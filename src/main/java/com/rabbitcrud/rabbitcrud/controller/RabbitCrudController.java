package com.rabbitcrud.rabbitcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RabbitCrudController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
