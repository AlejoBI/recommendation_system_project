package com.apiweb.controller;

import com.apiweb.model.CursoModel;
import com.apiweb.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiweb/v1/curso")

public class CursoController {
    @Autowired
    private ICursoService cursoService;

    @PostMapping("/")
    public ResponseEntity<String> crearCurso(@RequestBody CursoModel curso){
        cursoService.crearCurso(curso);
        return new ResponseEntity<String>(cursoService.crearCurso(curso), HttpStatus.OK);
    }
}
