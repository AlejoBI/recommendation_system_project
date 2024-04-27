package com.example.apiweb.controller;

import com.example.apiweb.exception.CamposInvalidosException;
import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.CursoModel;
import com.example.apiweb.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiweb/v1/curso")
@CrossOrigin
public class CursoController {
    @Autowired
    private ICursoService cursoService;

    //Crear un curso
    @PostMapping("/")
    public ResponseEntity<String> crearCurso(@RequestBody CursoModel curso) {
        cursoService.crearCurso(curso);
        return new ResponseEntity<String>(cursoService.crearCurso(curso), HttpStatus.OK);
    }

    //Listar Cursos
    @GetMapping("/")
    public ResponseEntity<List<CursoModel>> listarCursos() {
        List<CursoModel> cursos = cursoService.listarCursos();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    //Consultar un curso por Id
    @GetMapping("/{cursoId}")
    public ResponseEntity<CursoModel> buscarCursoPorId(@PathVariable Integer cursoId) {
        CursoModel curso = this.cursoService.obtenerCursoPorId(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el curso con el id " + cursoId));
        return ResponseEntity.ok(curso);
    }

    //Actualizar la información básica del curso
    @PutMapping("/{cursoId}")
    public ResponseEntity<String> actualizarCursoPorId(@PathVariable Integer cursoId, @RequestBody CursoModel detallesCurso) {
        CursoModel curso = this.cursoService.obtenerCursoPorId(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error!. No se encontró el curso con el id " + cursoId));
        //Obtenemos los datos que se van actualizar del curso y que son enviados del json
        String nombreActualizar = detallesCurso.getNombre_curso();
        String modalidadActualizar = detallesCurso.getModalidad();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar != null && !nombreActualizar.isEmpty() && modalidadActualizar != null && !modalidadActualizar.isEmpty()) {
            //Asignamos los valores que vamos actualizar del curso
            curso.setNombre_curso(nombreActualizar);
            curso.setModalidad(modalidadActualizar);
            //Guardamos los cambios
            return new ResponseEntity<String>(cursoService.actualizarCursoPorId(curso), HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! El nombre y la modalidad de el curso no pueden estar vacio");
        }
    }
}