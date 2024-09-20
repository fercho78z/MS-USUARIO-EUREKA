package com.msvc.usuario.controllers;


import com.msvc.usuario.entity.Calificacion;
import com.msvc.usuario.entity.Usuario;
import com.msvc.usuario.service.UsuarioService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//@Import(FeignClierentsConfiguration.class)
@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuarioRequest){
        Usuario usuario = usuarioService.saveUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String usuarioId){
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        return ResponseEntity.ok(usuario);
    }
    
   /* 
    @RequestMapping(value = "/calificaciones", method = RequestMethod.POST)
    public  ResponseEntity<Calificacion> guardarCalificacion(@RequestBody Calificacion calificacion) throws URISyntaxException {
        return calificacion.addCustomer(new URI("http://localhost:8082/califiaciones"), calificacion);
    }*/

    
  /*  int cantidadReintentos = 1;
    @GetMapping("/{usuarioId}")
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String usuarioId){
        log.info("Listar un solo usuario : UsuarioController");
        log.info("Cantidad reintentos : {}",cantidadReintentos);
        cantidadReintentos ++;
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<Usuario> ratingHotelFallback(String usuarioId,Exception exception){
      log.info("El respaldo se ejecuta porque el servicio esta inactivo : ",exception.getMessage());
      Usuario usuario = Usuario.builder()
              .email("root1@gmail.com")
              .nombre("root")
              .informacion("Este usuario se crea por defecto cuando un servicio se cae")
              .usuarioId("1234")
              .build();
      return new ResponseEntity<>(usuario,HttpStatus.OK);
    }*/
}
