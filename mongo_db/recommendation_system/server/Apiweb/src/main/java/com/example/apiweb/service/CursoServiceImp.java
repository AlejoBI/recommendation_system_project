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
        this.cursoRepository.save(curso);
        return "El curso " + curso.getNombre_curso() + " fue creado exitosamente";
    }

    @Override
    public List<CursoModel> listarCursos() {
        return this.cursoRepository.findAll();
    }

    @Override
    public Optional<CursoModel> obtenerCursoPorId(int cursoId) {
        return this.cursoRepository.findById(cursoId);
    }

    @Override
    public String eliminarCursoPorId(int cursoId) {
        Optional<CursoModel> cursoRef = this.cursoRepository.findById(cursoId);
        this.cursoRepository.deleteById(cursoId);
        return "El curso " + cursoRef.get().getCurso_id() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarCursoPorId(CursoModel curso) {
        this.cursoRepository.save(curso);
        return "El curso con id " + curso.getCurso_id() + " fue actualizado con exito.";
    }
}
