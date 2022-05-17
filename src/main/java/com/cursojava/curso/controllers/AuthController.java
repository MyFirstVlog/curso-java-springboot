package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired //hace que la clase usarioDaoimp crea objeto y lo guarda en la varibale Inyección dependencias
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;  // Con esto aplico la inyeccion de dependencias

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        //el @ tag de arriba del body hace convertir el parametro de entrada en un json directamente

        Usuario usuarioLogged =  usuarioDao.obtenerUsuarioPorCrendenciales(usuario);

        if(usuario != null){
            //Create jwt and return token
            return jwtUtil.create(String.valueOf(usuarioLogged.getId()), usuarioLogged.getEmail());

        }

        return "Fail";
    }
}
