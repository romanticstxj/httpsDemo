package com.example.demo.controller;

import com.example.demo.config.AmazonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadingListController {

    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(AmazonProperties amazonProperties) {
        this.amazonProperties = amazonProperties;
    }

    @GetMapping("/read")
    public String read(){
        System.out.println(amazonProperties.getAssociateId());
        return "success";
    }
}
