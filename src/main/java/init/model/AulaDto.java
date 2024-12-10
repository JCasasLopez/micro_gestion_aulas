package init.model;

import org.springframework.stereotype.Component;

@Component
public class AulaDto {
	private int idAula;
	private String nombre;
	private int capacidad;
	private boolean proyector;
	private boolean altavoces;
	
	public AulaDto(int idAula, String nombre, int capacidad, boolean proyector, boolean altavoces) {
		super();
		this.idAula = idAula;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.proyector = proyector;
		this.altavoces = altavoces;
	}

	public AulaDto() {
		super();
	}

	public int getIdAula() {
		return idAula;
	}

	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isProyector() {
		return proyector;
	}

	public void setProyector(boolean proyector) {
		this.proyector = proyector;
	}

	public boolean isAltavoces() {
		return altavoces;
	}

	public void setAltavoces(boolean altavoces) {
		this.altavoces = altavoces;
	}
}
