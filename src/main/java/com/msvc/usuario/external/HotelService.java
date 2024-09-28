package com.msvc.usuario.external;

import com.msvc.usuario.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//se usa CONFIG-PROD ya que se esta usando ConfigServer para mandar llamar desde el repositorio 
//de GIT HUB el application.properties tal como se indica en el application.propoerties del MicroServicio-Hotel
//@FeignClient(name = "CONFIG-PROD") 
@FeignClient(name = "MicroServicio-Hotel")
public interface HotelService {

    @GetMapping("/hoteles/{hotelId}")
    Hotel getHotel(@PathVariable String hotelId);

}
