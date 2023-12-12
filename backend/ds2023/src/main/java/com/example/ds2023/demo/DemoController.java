package com.example.ds2023.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from secured endpoint");
    }
}
