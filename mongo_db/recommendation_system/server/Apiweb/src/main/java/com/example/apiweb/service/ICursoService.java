package com.example.apiweb.service;

import com.example.apiweb.model.CursoModel;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
    String crearCurso(CursoModel curso);
    List<CursoModel> listarCursos();
    Optional<CursoModel> obtenerCursoPorId(int cursoId);
    String eliminarCursoPorId(int cursoId);
    String actualizarCursoPorId(CursoModel curso);
}