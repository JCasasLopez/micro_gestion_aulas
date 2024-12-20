package init.service;

import init.model.AulaDto;
import init.model.BloqueoDto;

public interface AulasService {
	boolean altaAula(AulaDto aula);
	boolean bajaAula(int idAula);
	boolean modificarAula(AulaDto aula);
	void bloquearHorario(BloqueoDto bloqueo);
}
