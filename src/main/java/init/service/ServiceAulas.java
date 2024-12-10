package init.service;

import java.time.LocalDateTime;

import init.model.AulaDto;

public interface ServiceAulas {
	boolean altaAula(AulaDto aula);
	boolean bajaAula(int idAula);
	boolean modificar(AulaDto aula);
	//Este método permite "bloquear" la disponibilidad de un aula para llevar a cabo trabajos de
	//mantenimiento, etc. Cualquier reserva que caiga dentro del período que bloquea, quedará
	//cancelada automáticamente, mandando una notificación a la persona que tenía la reserva.
	//Como a todos los efectos es una reserva sin límite de tiempo, se realizar a través del 
	//microservicio "gestion_reservas".
	void bloquearHorario(int idAula, LocalDateTime horaInicio, LocalDateTime horaFin);
}
