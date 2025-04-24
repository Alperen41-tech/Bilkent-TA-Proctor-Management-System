package com.cs319group3.backend.Controllers.RequestControllers;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taFromDeanRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.RequestControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAFromDeanRequestController {







}
