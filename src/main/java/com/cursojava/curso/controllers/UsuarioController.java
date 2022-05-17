package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired //hace que la clase usarioDaoimp crea objeto y lo guarda en la varibale Inyección dependencias
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}")
    public Usuario getUsuario(@PathVariable Long id){
        System.out.println(id);
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Kora");
        usuario.setApellido("Estrada");
        usuario.setEmail("alejo@alejos.com");
        usuario.setTelefono("23121");
        return usuario;
    }

    private boolean validarToken( String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value = "api/usuario")
    public List<Usuario> ListUsuario(@RequestHeader( value = "Authorization") String token){

        //Verify if token is correct

        System.out.println(token);

        if(!validarToken(token)){
            return null;
        }

        return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuario", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario){
        //el @ tag de arriba del body hace convertir el parametro de entrada en un json directamente

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/usuario/edit/{id}")
    public Usuario editUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setNombre("Kora");
        usuario.setApellido("Estrada");
        usuario.setEmail("alejo@alejos.com");
        usuario.setTelefono("23121");
        return usuario;
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public void deleteUsuario(@RequestHeader(value = "Authorization") String token,@PathVariable Long id){
        if(validarToken(token)){
            usuarioDao.deleteUsuario(id);
        }
        return;
    }

}
