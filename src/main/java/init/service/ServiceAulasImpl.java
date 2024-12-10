package init.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import init.dao.AulasDao;
import init.model.AulaDto;
import init.utilidades.Mapeador;

@Service
public class ServiceAulasImpl implements ServiceAulas {
	
	AulasDao aulasDao;
	Mapeador mapeador;
	RestClient restClient;
	String url="http://servicio-reservas/reservas/";

	public ServiceAulasImpl(AulasDao aulasDao, Mapeador mapeador, RestClient restClient) {
		this.aulasDao = aulasDao;
		this.mapeador = mapeador;
		this.restClient = restClient;
	}

	@Override
	public boolean altaAula(AulaDto aula) {
		if(aulasDao.save(mapeador.aulaDtoToEntity(aula)) != null) {
			return true;
		}
		return false;
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
	public void bloquearHorario(int idAula, LocalDateTime horaInicio, LocalDateTime horaFin) {
		// TODO Auto-generated method stub

	}

}
