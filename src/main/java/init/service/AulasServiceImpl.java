package init.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import init.dao.AulasDao;
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
		return aulasDao.save(mapeador.aulaDtoToEntity(aula)) != null;
	}

	@Override
	public boolean bajaAula(int idAula) {
		if(aulasDao.existsById(idAula)) {
			aulasDao.deleteById(idAula);
			return true;
		}
		return false;
	}

	@Override
	public boolean modificar(AulaDto aula) {
		if(aulasDao.existsById(aula.getIdAula())) {
			aulasDao.save(mapeador.aulaDtoToEntity(aula));
			return true;
		}
		return false;
	}

	@Override
	public void bloquearHorario(BloqueoDto bloqueoHorario) {
		//Este método permite "bloquear" la disponibilidad de un aula para llevar a cabo trabajos de
		//mantenimiento, etc. Cualquier reserva que caiga dentro del período que bloquea, quedará
		//cancelada automáticamente, mandando una notificación a la persona que tenía la reserva.
		//Como a todos los efectos es una reserva sin límite de tiempo, se realizar a través del 
		//microservicio "gestion_reservas".
		
		restClient
					.post()
					.uri(url + "/hacerReserva")
					.contentType(MediaType.APPLICATION_JSON)
					.body(bloqueoHorario)
					.retrieve()
					.toBodilessEntity();

	}

}
