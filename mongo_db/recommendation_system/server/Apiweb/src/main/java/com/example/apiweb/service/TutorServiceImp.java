package com.example.apiweb.service;

import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.TutorModel;
import com.example.apiweb.repository.ITutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TutorServiceImp implements ITutorService{
    @Autowired
    ITutorRepository tutorRepository;

    @Override
    public String crearTutor(TutorModel tutor) {
        this.tutorRepository.save(tutor);
        return "El tutor " + tutor.getTutor_name() + " fue creado exitosamente";
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
}
