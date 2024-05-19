package game;

import java.time.LocalDate;

public class Entrenador {
	
	private static int contador = 0;
	
	private int idEntrenador;
	private String nombreEntrenador;
	private LocalDate fechaCreacion;

	public Entrenador() {
		this.fechaCreacion = LocalDate.now();
	}
	
	public Entrenador(String nombreEntrenador) {
		this.idEntrenador = ++contador;
		this.nombreEntrenador = nombreEntrenador;
		this.fechaCreacion = LocalDate.now();
	}

	public int getIdEntrenador() {
		return idEntrenador;
	}

	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	public String getNombreEntrenador() {
		return nombreEntrenador;
	}

	public void setNombreEntrenador(String nombreEntrenador) {
		this.nombreEntrenador = nombreEntrenador;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


}
