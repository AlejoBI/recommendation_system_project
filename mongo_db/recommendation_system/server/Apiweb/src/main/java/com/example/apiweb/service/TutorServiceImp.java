package com.example.apiweb.service;

import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.TutorModel;
import com.example.apiweb.repository.ITutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class TutorServiceImp implements ITutorService{
    @Autowired
    ITutorRepository tutorRepository;

    @Override
    public String crearTutor(TutorModel tutor) {
        this.tutorRepository.save(tutor);
        return "El tutor " + tutor.getNombre_tutor() + " fue creado exitosamente";
    }

    @Override
    public List<TutorModel> listarTutores() {
        return this.tutorRepository.findAll();
    }

    @Override
    public Optional<TutorModel> obtenerTutorPorId(int tutorId) {
        return this.tutorRepository.findById(tutorId);
    }

    @Override
    public String eliminarTutorPorId(int tutorId) {
        Optional<TutorModel> tutorRef = this.tutorRepository.findById(tutorId);
        this.tutorRepository.deleteById(tutorId);
        return "El tutor " + tutorRef.get().getTutor_id() + " fue eliminado con exito.";
    }

    @Override
    public String actualizarTutorPorId(TutorModel tutor) {
        this.tutorRepository.save(tutor);
        return "El tutor con id " + tutor.getTutor_id() + " fue actualizado con exito.";
    }

    @Override
    public String agregarCursoATutor(int tutorId, CursoModel curso) {
        Optional<TutorModel> tutorOptional = this.tutorRepository.findById(tutorId);
        if (tutorOptional.isPresent()) {
            TutorModel tutor = tutorOptional.get();

            Map<String, Object> cursoMap = new HashMap<>();
            cursoMap.put("curso_id", curso.getCurso_id());

            List<Map<String, Object>> cursos = tutor.getCursos();
            if (cursos == null) {
                cursos = new ArrayList<>();
            }
            cursos.add(cursoMap);

            tutor.setCursos(cursos);
            this.tutorRepository.save(tutor);

            return "Curso agregado con éxito al tutor con id " + tutorId;
        } else {
            throw new RecursoNoEncontradoException("Error! No se encontró el tutor con el id " + tutorId);
        }
    }

    @Override
    public List<TutorModel> mostrarTutoresCursosMayoresAN(Double ratings) {
        return this.tutorRepository.listarTutoresCursosMayoresAN(ratings);
    }
}
