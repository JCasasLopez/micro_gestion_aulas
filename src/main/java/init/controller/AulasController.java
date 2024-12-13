package init.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import init.exception.MicroserviceCommunicationException;
import init.model.AulaDto;
import init.model.BloqueoDto;
import init.service.AulasService;

@CrossOrigin("*")
@RestController
public class AulasController {
	RestClient restClient;
	AulasService aulasService;
	
	public AulasController(RestClient restClient, AulasService aulasService) {
		this.restClient = restClient;
		this.aulasService = aulasService;
	}
	
	//Métodos auxiliares que se encargan de llamar al micro de usuarios y devolver la respuesta
	
	private boolean validarRole(String autorizacion) {
		String url="http://servicio-usuarios/usuarios/";
		try {
			return restClient
			.get()
			.uri(url + "validarAdmin")
			.header("Authorization", autorizacion)
			.retrieve()
			.body(Boolean.class);
			
		}catch (Exception ex) {
			//Por seguridad, en caso de que haya algún problema se devuelve false (no es ADMIN)
			return false;  
		}
	}
	
	private ResponseEntity<String> validarAdmin(String autorizacion) {
	    if (!validarRole(autorizacion)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("Solo los administradores pueden acceder a este método");
	    }
	    return null; 
	}
	
	
	//Estos son los métodos de Controller propiamente dichos
	
	@PostMapping(value="altaAula", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> altaAula(@RequestBody AulaDto aula, 
											@RequestHeader("Authorization") String autorizacion){
		
		ResponseEntity<String> validacion = validarAdmin(autorizacion);
		if(validacion != null) {
		    return validacion;
		}
        
        try {
            aulasService.altaAula(aula);
            return ResponseEntity.ok("Aula creada correctamente");
            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear el aula");
        }
	}
	
	@DeleteMapping(value="bajaAula")
	public ResponseEntity<String> bajaAula(@RequestParam int idAula, 
											@RequestHeader("Authorization") String autorizacion){
		
		ResponseEntity<String> validacion = validarAdmin(autorizacion);
		if(validacion != null) {
		    return validacion;
		}
        
        try {
            aulasService.bajaAula(idAula);
            return ResponseEntity.ok("Aula borrada correctamente");
            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al borrar el aula");
        }
	}
	
	@PostMapping(value="modificarAula", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> modificarAula(@RequestBody AulaDto aula, 
											@RequestHeader("Authorization") String autorizacion){
		
		ResponseEntity<String> validacion = validarAdmin(autorizacion);
		if(validacion != null) {
		    return validacion;
		}
        
        try {
            aulasService.altaAula(aula);
            return ResponseEntity.ok("Aula modificada correctamente");
            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al modificar el aula");
        }
	}
	
	@PostMapping(value="bloquearHorario", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> bloquearHorario(@RequestBody BloqueoDto bloqueoHorario, 
											@RequestHeader("Authorization") String autorizacion){
		
		ResponseEntity<String> validacion = validarAdmin(autorizacion);
		if(validacion != null) {
		    return validacion;
		}
        
        try {
            aulasService.bloquearHorario(bloqueoHorario);
            return ResponseEntity.ok("Horario bloqueado correctamente");
            
        } catch (MicroserviceCommunicationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al llamar al microservicio Reservas");
        }
        
	}
	
}
