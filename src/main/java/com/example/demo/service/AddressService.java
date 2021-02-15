package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public String addAddress(String address){
        System.out.println(address);
        return address + " added";
    }
}
