package game;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * La clase Entrenador representa a un entrenador en el juego. Cada entrenador
 * tiene un identificador único, un nombre y una fecha de creación.
 * 
 * @author Jaime Martinez Fernández
 */

public class Entrenador {

	static Scanner sc = new Scanner(System.in);
	private static int contador = 0;

	private int idEntrenador;
	private String nombreEntrenador;
	private LocalDate fechaCreacion;

	/**
	 * Crea un nuevo entrenador con la fecha de creación del momento en que se crea
	 * la instacia.
	 */

	public Entrenador() {
		this.fechaCreacion = LocalDate.now();
	}

	/**
	 * Crea un nuevo entrenador con un nombre específico y la fecha de creación del
	 * momento en que se crea la instacia. Tiene un id autoincremental.
	 * 
	 * @param nombreEntrenador Nombre del entrenador
	 */

	public Entrenador(String nombreEntrenador) {
		this.idEntrenador = ++contador;
		this.nombreEntrenador = nombreEntrenador;
		this.fechaCreacion = LocalDate.now();
	}

	/**
	 * Devuelve el identificar del entrenador.
	 * 
	 * @return Identificador del entrenador
	 */

	public int getIdEntrenador() {
		return idEntrenador;
	}

	/**
	 * Establece el identificador del entrenador.
	 * 
	 * @param idEntrenador Identificador del entrenador
	 */

	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	/**
	 * Devuelve el nombre del entrenador.
	 * 
	 * @return Nombre del entrenador
	 */

	public String getNombreEntrenador() {
		return nombreEntrenador;
	}

	/**
	 * Establece el nombre del entrenador.
	 * 
	 * @param nombreEntrenador Nombre del entrenador
	 */

	public void setNombreEntrenador(String nombreEntrenador) {
		this.nombreEntrenador = nombreEntrenador;
	}

	/**
	 * Devuelve la fecha de creación.
	 * 
	 * @return Fecha de creación
	 */

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece la fecha de creación.
	 * 
	 * @param fechaCreacion Fecha de creación
	 */

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Método estático que permite al usuario crear un nuevo entrenador. Solicita al
	 * usuario que introduzca el nombre y crea una nueva instancia de Entrenador con
	 * ese nombre, luego lo añade a la gestión de entrenadores.
	 */

	public static void crearEntrenador() {
		String nombre;

		System.out.print("Introduce el nombre del entrenador: ");
		nombre = sc.nextLine();

		Entrenador entrenador = new Entrenador(nombre);

		GestionEntrenadores.aniadirEntrenador(entrenador);
	}

}
