package init.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import init.dao.AulasDao;
import init.exception.AulaDatabaseException;
import init.exception.MicroserviceCommunicationException;
import init.model.AulaDto;
import init.model.BloqueoDto;
import init.utilidades.Mapeador;

@Service
public class AulasServiceImpl implements AulasService {
	
	AulasDao aulasDao;
	Mapeador mapeador;
	RestClient restClient;
	String url="http://servicio-reservas/reservas/";

	public AulasServiceImpl(AulasDao aulasDao, Mapeador mapeador, RestClient restClient) {
		this.aulasDao = aulasDao;
		this.mapeador = mapeador;
		this.restClient = restClient;
	}

	@Override
	public boolean altaAula(AulaDto aula) {	
		try {
			aulasDao.save(mapeador.aulaDtoToEntity(aula));
	        return true;
			
		}catch(RuntimeException ex) {
			throw new AulaDatabaseException("Error en la base de datos al intentar guardar el aula");
		}
	}

	@Override
	public boolean bajaAula(int idAula) {
		try {
			if(aulasDao.existsById(idAula)) {
				aulasDao.deleteById(idAula);
				return true;
			}
			return false;
			
		}catch(RuntimeException ex) {
			throw new AulaDatabaseException("Error en la base de datos al intentar eliminar el aula");
		}
	}

	@Override
	public boolean modificarAula(AulaDto aula) {
		try {
			if(aulasDao.existsById(aula.getIdAula())) {
				aulasDao.save(mapeador.aulaDtoToEntity(aula));
				return true;
			}
			return false;
			
		}catch(RuntimeException ex) {
			throw new AulaDatabaseException("Error en la base de datos al intentar modificar el aula");
		}
		
	}

	@Override
	public void bloquearHorario(BloqueoDto bloqueoHorario) {
		//Este método permite "bloquear" la disponibilidad de un aula para llevar a cabo trabajos de
		//mantenimiento, etc. Cualquier reserva que caiga dentro del período que bloquea, quedará
		//cancelada automáticamente, mandando una notificación a la persona que tenía la reserva.
		//Como a todos los efectos es una reserva sin límite de tiempo, se realizar a través del 
		//microservicio "gestion_reservas".
		
		try{
			restClient
				.post()
				.uri(url + "/hacerReserva")
				.contentType(MediaType.APPLICATION_JSON)
				.body(bloqueoHorario)
				.retrieve()
				.toBodilessEntity();
			
		}catch(RuntimeException ex) {
			throw new MicroserviceCommunicationException("Error al comunicar con el servicio de reservas para bloquear el horario");
		}
					
	}

}
