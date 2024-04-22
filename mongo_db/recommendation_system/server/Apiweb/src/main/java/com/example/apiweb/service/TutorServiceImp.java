package com.example.apiweb.service;

import com.example.apiweb.model.TutorModel;

import java.util.List;
import java.util.Optional;

public class TutorServiceImp implements ITutorService{
    @Override
    public String crearTutor(TutorModel tutor) {
        return null;
    }

    @Override
    public List<TutorModel> listarTutores() {
        return null;
    }

    @Override
    public Optional<TutorModel> obtenerTutorPorId(int tutorId) {
        return Optional.empty();
    }

    @Override
    public String eliminarTutorPorId(int tutorId) {
        return null;
    }

    @Override
    public String actualizarTutorPorId(TutorModel tutor) {
        return null;
    }
}
