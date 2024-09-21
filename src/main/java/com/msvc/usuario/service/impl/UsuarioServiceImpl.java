package com.msvc.usuario.service.impl;

import com.msvc.usuario.entity.Hotel;
import com.msvc.usuario.entity.Calificacion;
import com.msvc.usuario.entity.Usuario;
import com.msvc.usuario.exception.ResourceNotFoundException;
import com.msvc.usuario.external.HotelService;
import com.msvc.usuario.repository.UsuarioRepository;
import com.msvc.usuario.service.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	    @Autowired
	    private RestTemplate restTemplate;

	    @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Autowired
	    private HotelService hotelService;

	    @Override
	    public Usuario saveUsuario(Usuario usuario) {
	        String randomUsuarioId = UUID.randomUUID().toString();
	        usuario.setUsuarioId(randomUsuarioId);
	        return usuarioRepository.save(usuario);
	    }

	    @Override
	    public List<Usuario> getAllUsuarios() {
	        return usuarioRepository.findAll();
	    }
	    
	    /*
	    @SuppressWarnings("unchecked")
		@Override
	    public Usuario getUsuario(String usuarioId) {
	        Usuario usuario = usuarioRepository.findById(usuarioId)
	                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID : " + usuarioId));

	        ArrayList<Calificacion> calificacionesDelUsuario = restTemplate.getForObject("http://localhost:8082/calificaciones/usuarios/"+usuario.getUsuarioId(),ArrayList.class);
	        logger.info("{}",calificacionesDelUsuario);
	        usuario.setCalificaciones(calificacionesDelUsuario);
	        return usuario;
	    }*/
	    @SuppressWarnings("unchecked")
	    @Override
	    public Usuario getUsuario(String usuarioId) {
	        Usuario usuario = usuarioRepository.findById(usuarioId)
	                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID : " + usuarioId));

	        //Usando RestTemplate
	        Calificacion[] calificacionesDelUsuario = restTemplate.getForObject("http://localhost:8082/calificaciones/usuarios/"+usuario.getUsuarioId(),Calificacion[].class);
	        List<Calificacion> calificaciones = Arrays.stream(calificacionesDelUsuario).collect(Collectors.toList());
	        List<Calificacion> listaCalificaciones = calificaciones.stream().map(calificacion -> {
	            System.out.println(calificacion.getHotelId());
	          
	            //Usando RestTemplate
	            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8081/hoteles/"+calificacion.getHotelId(),Hotel.class);
	            Hotel hotel = forEntity.getBody();
	            logger.info("Respuesta con codigo de estado : {}",forEntity.getStatusCode());

	            //Usando OpenFeign
	            //Hotel hotel = hotelService.getHotel(calificacion.getHotelId());
	            
	            calificacion.setHotel(hotel);

	            return calificacion;
	        }).collect(Collectors.toList());

	        usuario.setCalificaciones(listaCalificaciones);

	        return usuario;
	    }

}
