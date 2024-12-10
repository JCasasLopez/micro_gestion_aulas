package init.model;

import java.time.LocalDateTime;

public class BloqueoDto {
	private int idBloqueo;
	private LocalDateTime horaInicio;
	private LocalDateTime horaFin;
	private int idAula;
	private int idUsuario;
	
	public BloqueoDto(int idBloqueo, LocalDateTime horaInicio, LocalDateTime horaFin, int idAula, int idUsuario) {
		this.idBloqueo = idBloqueo;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.idAula = idAula;
		this.idUsuario = idUsuario;
	}

	public BloqueoDto() {
		super();
	}

	public int getIdBloqueo() {
		return idBloqueo;
	}

	public void setIdBloqueo(int idBloqueo) {
		this.idBloqueo = idBloqueo;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalDateTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}

	public int getIdAula() {
		return idAula;
	}

	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

}
