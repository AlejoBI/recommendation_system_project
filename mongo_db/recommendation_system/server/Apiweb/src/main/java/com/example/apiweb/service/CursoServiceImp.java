package com.example.apiweb.service;

import com.example.apiweb.model.CursoModel;
import com.example.apiweb.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CursoServiceImp implements ICursoService{
    @Autowired
    ICursoRepository cursoRepository;

    @Override
    public String crearCurso(CursoModel curso) {
        return null;
    }

    @Override
    public List<CursoModel> listarCursos() {
        return null;
    }

    @Override
    public Optional<CursoModel> obtenerCursoPorId(int cursoId) {
        return Optional.empty();
    }

    @Override
    public String eliminarCursoPorId(int cursoId) {
        return null;
    }

    @Override
    public String actualizarCursoPorId(CursoModel curso) {
        return null;
    }
}
