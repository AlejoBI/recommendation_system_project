package com.example.apiweb.controller;

import com.example.apiweb.exception.CamposInvalidosException;
import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.TutorModel;
import com.example.apiweb.service.ITutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiweb/v1/tutor")
@CrossOrigin
public class TutorController {
    @Autowired
    private ITutorService tutorService;
    //Crear un tutor
    @PostMapping("/")
    public ResponseEntity<String> crearTutor(@RequestBody TutorModel tutor) {
        tutorService.crearTutor(tutor);
        return new ResponseEntity<String>(tutorService.crearTutor(tutor), HttpStatus.OK);
    }

    //Listar tutores
    @GetMapping("/")
    public ResponseEntity<List<TutorModel>> listarTutores() {
        List<TutorModel> tutores = tutorService.listarTutores();
        return new ResponseEntity<>(tutores, HttpStatus.OK);
    }

    //Consultar un tutor por Id
    @GetMapping("/{tutorId}")
    public ResponseEntity<TutorModel> buscarTutorPorId(@PathVariable Integer tutorId) {
        TutorModel tutor = this.tutorService.obtenerTutorPorId(tutorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el tutor con el id " + tutorId));
        return ResponseEntity.ok(tutor);
    }

    //Actualizar la información básica del tutor
    @PutMapping("/{tutorId}")
    public ResponseEntity<String> actualizarTutorPorId(@PathVariable Integer tutorId, @RequestBody TutorModel detallesTutor) {
        TutorModel tutor = this.tutorService.obtenerTutorPorId(tutorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el tutor con el id " + tutorId));
        //Obtenemos los datos que se van actualizar del tutor y que son enviados del json
        String nombreActualizar = detallesTutor.getTutor_name();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar != null && !nombreActualizar.isEmpty()) {
            //Asignamos los valores que vamos actualizar del tutor
            tutor.setTutor_name(nombreActualizar);
            //Guardamos los cambios
            return new ResponseEntity<String>(tutorService.actualizarTutorPorId(tutor), HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! El nombre del tutor no puede estar vacio");
        }
    }
}
