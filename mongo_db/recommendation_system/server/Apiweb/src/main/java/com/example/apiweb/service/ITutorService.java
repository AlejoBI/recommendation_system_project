package com.example.apiweb.service;


import com.example.apiweb.model.TutorModel;

import java.util.List;
import java.util.Optional;

public interface ITutorService {
    String crearTutor(TutorModel tutor);
    List<TutorModel> listarTutores();
    Optional<TutorModel> obtenerTutorPorId(int tutorId);
    String eliminarTutorPorId(int tutorId);
    String actualizarTutorPorId(TutorModel tutor);
}
