package com.example.demo.controller;

import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other")
public class OtherController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/addAddress")
    public String addAddress(String address){
        addressService.addAddress(address);
        return "success";
    }
}
