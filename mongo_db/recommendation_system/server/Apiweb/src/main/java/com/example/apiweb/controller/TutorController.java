package com.example.apiweb.controller;

import com.example.apiweb.service.ITutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Apiweb/v1/tutor")
@CrossOrigin
public class TutorController {
    @Autowired
    private ITutorService tutorService;


}
