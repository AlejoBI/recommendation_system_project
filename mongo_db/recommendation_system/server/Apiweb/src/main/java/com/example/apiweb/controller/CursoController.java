package com.example.apiweb.controller;

import com.example.apiweb.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Apiweb/v1/curso")
@CrossOrigin
public class CursoController {
    @Autowired
    private ICursoService cursoService;


}
