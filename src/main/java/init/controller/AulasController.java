package init.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import init.model.AulaDto;
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
	
	private boolean validarRole(String autorizacion) {
		//Llamamos al micro usuarios para preguntar si el usuario es ADMIN
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
	
	@PostMapping(value="altaAula", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> altaAula(@RequestBody AulaDto aula, 
											@RequestHeader("Authorization") String autorizacion){
		if (!validarRole(autorizacion)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                               	 .body("No tienes permisos para realizar esta acción");
        }
        
        try {
            aulasService.altaAula(aula);
            return ResponseEntity.ok("Aula creada correctamente");
            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear el aula: " + ex.getMessage());
        }
	}
}
