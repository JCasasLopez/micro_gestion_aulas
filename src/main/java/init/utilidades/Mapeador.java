package init.utilidades;

import org.springframework.stereotype.Component;

import init.entities.Aula;
import init.model.AulaDto;

@Component
public class Mapeador {
	
	public AulaDto aulaEntityToDto(Aula aula) {
		return new AulaDto(aula.getIdAula(),
							aula.getNombre(),
							aula.getCapacidad(),
							aula.isProyector(),
							aula.isAltavoces());
	}
	
	public Aula aulaDtoToEntity(AulaDto aula) {
		return new Aula(0,
						aula.getNombre(),
						aula.getCapacidad(),
						aula.isProyector(),
						aula.isAltavoces());
	}
	
}
