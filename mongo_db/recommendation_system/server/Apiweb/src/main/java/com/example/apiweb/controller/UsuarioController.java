package com.example.apiweb.controller;

import com.example.apiweb.exception.CamposInvalidosException;
import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.TutorModel;
import com.example.apiweb.model.UsuarioModel;
import com.example.apiweb.service.IUsuarioService;
import com.example.apiweb.service.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiweb/v1/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    //Crear un usuario
    @PostMapping("/")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        usuarioService.crearUsuario(usuario);
        return new ResponseEntity<String>(usuarioService.crearUsuario(usuario), HttpStatus.OK);
    }

    //Listar Usuarios
    @GetMapping("/")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //Consultar un usuario por Id
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Integer usuarioId) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el usuario con el id " + usuarioId));
        return ResponseEntity.ok(usuario);
    }

    //Actualizar la información básica del usuario
    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> actualizarUsuarioPorId(@PathVariable Integer usuarioId, @RequestBody UsuarioModel detallesUsuario) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el usuario con el id " + usuarioId));
        //Obtenemos los datos que se van actualizar del usuario y que son enviados del json
        String nombreActualizar = detallesUsuario.getNombre_usuario();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar != null && !nombreActualizar.isEmpty()) {
            //Asignamos los valores que vamos actualizar del tutor
            usuario.setNombre_usuario(nombreActualizar);
            //Guardamos los cambios
            return new ResponseEntity<String>(usuarioService.actualizarUsuarioPorId(usuario), HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! El nombre del tutor no puede estar vacio");
        }
    }
}
