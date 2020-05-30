package com.xebia.writerpad.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController
{
    @GetMapping
    @ApiOperation(value = "Root path url")
    public String index() {
        return "Welcome to WriterPad. Please visit http://localhost:8080/swagger-ui.html for API documentation.";
    }
}
