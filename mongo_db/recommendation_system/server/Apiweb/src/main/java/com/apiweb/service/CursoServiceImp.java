package com.apiweb.service;

import com.apiweb.model.CursoModel;
//import com.apiweb.repository.ICursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImp implements ICursoService{
    @Autowired
    //ICursoRepository cursoRepository;

    @Override
    public String crearCurso(CursoModel curso) {
        //this.cursoRepository.save(curso);
        return "El curso " + curso.getNombre_curso() + " fue creado exitosamente";
    }

    @Override
    public List<CursoModel> listarCursos() {
        return List.of();
    }

    @Override
    public Optional<CursoModel> obtenerCursoPorId(int cursoId) {
        return Optional.empty();
    }

    @Override
    public String eliminarCursoPorId(int cursoId) {
        return "";
    }

    @Override
    public String actualizarCursoPorId(CursoModel curso) {
        return "";
    }
}
