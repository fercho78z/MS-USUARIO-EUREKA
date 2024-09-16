package com.msvc.usuario.external;


import com.msvc.usuario.entity.Calificacion;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "MicroServicio-Calificacion-1")
public interface CalificacionService {
	
	@GetMapping("/calificacion/{hotelId}")
	Calificacion getCalificacion(@PathVariable String calificacionId);
    
    @PostMapping
    public ResponseEntity<Calificacion> guardarCalificacion(Calificacion calificacion);

    @PutMapping("/calificaciones/{calificacionId}")
    public ResponseEntity<Calificacion> actualizarCalificacion(@PathVariable String calificacionId,Calificacion calificacion);

    @DeleteMapping("/calificaciones/{calificacionId}")
    public void eliminarCalificacion(@PathVariable String calificacionId);

}

