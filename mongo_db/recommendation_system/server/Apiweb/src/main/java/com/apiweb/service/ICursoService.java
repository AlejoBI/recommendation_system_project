package com.apiweb.service;

import com.apiweb.model.CursoModel;

import java.util.List;
import java.util.Optional;


public interface ICursoService {
    //funcionalidades /logica que  realizara esta entidad
    String crearCurso(CursoModel curso);
    List<CursoModel> listarCursos();
    Optional<CursoModel> obtenerCursoPorId(int cursoId);
    String eliminarCursoPorId(int cursoId);
    String actualizarCursoPorId(CursoModel curso);
}
