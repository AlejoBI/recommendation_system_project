package com.example.apiweb.controller;

import com.example.apiweb.exception.CamposInvalidosException;
import com.example.apiweb.exception.RecursoNoEncontradoException;
import com.example.apiweb.model.CursoModel;
import com.example.apiweb.model.TutorModel;
import com.example.apiweb.model.UsuarioModel;
import com.example.apiweb.service.ICursoService;
import com.example.apiweb.service.IUsuarioService;
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
    @Autowired
    private ICursoService cursoService;

    //Crear un usuario
    @PostMapping("/")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        usuarioService.crearUsuario(usuario);
        return new ResponseEntity<String>("Usuario creado correctamente.", HttpStatus.OK);
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
        String carreraActualizar = detallesUsuario.getCarrera();
        String semestreActualizar = detallesUsuario.getSemestre();

        //Verificamos que estos campos actualizar no sean nulos o vacios y controlamos la excepcion
        if (nombreActualizar != null && !nombreActualizar.isEmpty() && carreraActualizar != null && !carreraActualizar.isEmpty() && semestreActualizar != null && !semestreActualizar.isEmpty()) {
            //Asignamos los valores que vamos actualizar del tutor
            usuario.setNombre_usuario(nombreActualizar);
            usuario.setCarrera(carreraActualizar);
            usuario.setSemestre(semestreActualizar);
            //Guardamos los cambios
            return new ResponseEntity<String>(usuarioService.actualizarUsuarioPorId(usuario), HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! Debes llenar todos los campos para actualizar el usuario.");
        }
    }

    // Agregar un curso a un usuario
    @PostMapping("/{usuarioId}/curso/{cursoId}")
    public ResponseEntity<String> agregarCursoAUsuario(@PathVariable Integer usuarioId, @PathVariable Integer cursoId) {
        CursoModel curso = this.cursoService.obtenerCursoPorId(cursoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el curso con el id " + cursoId));
        usuarioService.agregarCursoAUsuario(usuarioId, curso);
        return new ResponseEntity<String>("Curso agregado correctamente al usuario.", HttpStatus.OK);
    }

    //Eliminar un usuario por Id
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Integer usuarioId) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el usuario con el id " + usuarioId));
        return new ResponseEntity<String>(usuarioService.eliminarUsuarioPorId(usuarioId),HttpStatus.OK);
    }
}
