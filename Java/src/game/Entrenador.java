package game;

import java.time.LocalDate;
import java.util.ArrayList;

public class Entrenador {

	private static int contador = 0;
	
	private int idEntrenador;
	private String nombreEntrenador;
	private LocalDate fechaCreacion;
	private ArrayList<Pokemon> equipo;
	private ArrayList<Objeto> mochila;
	private ArrayList<Medalla> estuche;

	public Entrenador() {
		this.fechaCreacion = LocalDate.now();
	}
	
	public Entrenador(String nombreEntrenador) {
		this.idEntrenador = ++contador;
		this.nombreEntrenador = nombreEntrenador;
		this.fechaCreacion = LocalDate.now();
		this.equipo = new ArrayList<Pokemon>(6);
		this.mochila = new ArrayList<Objeto>();
		this.estuche = new ArrayList<Medalla>(8);
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

	public ArrayList<Pokemon> getEquipo() {
		return equipo;
	}

	public void setEquipo(ArrayList<Pokemon> equipo) {
		this.equipo = equipo;
	}

	public ArrayList<Objeto> getMochila() {
		return mochila;
	}

	public void setMochila(ArrayList<Objeto> mochila) {
		this.mochila = mochila;
	}

	public ArrayList<Medalla> getEstuche() {
		return estuche;
	}

	public void setEstuche(ArrayList<Medalla> estuche) {
		this.estuche = estuche;
	}

	@Override
	public String toString() {
		return 	"=========================================================\n" + 
				this.idEntrenador + "º | " + this.nombreEntrenador + "  " + this.estuche + "\n" + this.equipo + "\nMochila: " + mochila.size() +
				"\n=========================================================";
	}

}
