package com.backend.api.controllers;

import com.backend.api.dtos.TestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {
    @GetMapping("/hello")
    @ResponseBody
    public TestDto sayHello() {
        final TestDto test = new TestDto();
        test.setMessage("hello");
        return test;
    }
}
