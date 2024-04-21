package com.example.apiweb.service;

import com.example.apiweb.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public class UsuarioServiceImp implements IUsuarioService{
    @Override
    public String crearUsuario(UsuarioModel usuario) {
        return null;
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return null;
    }

    @Override
    public Optional<UsuarioModel> obtenerUsuarioPorId(int usuarioId) {
        return Optional.empty();
    }

    @Override
    public String eliminarUsuarioPorId(int usuarioId) {
        return null;
    }

    @Override
    public String actualizarUsuarioPorId(UsuarioModel usuario) {
        return null;
    }
}
