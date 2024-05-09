package game;

import java.util.Scanner;

public class App {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Variables
		String opcion = "";

		do {

			System.out.println("\n========= POKEMON TRAINERS =========\n");

			GestionEntrenadores.imprimirEntrenadores();

			System.out.println("\n====================================\n");

			System.out.println("1) Registrar entrenador");
			System.out.println("2) Eliminar entrenador");
			System.out.println("3) Modificar entrenador");
			System.out.println("4) Gestionar equipo");
			System.out.println("5) Ver estuche de entrenador");
			System.out.println("6) Ver equipo de entrenador");
			System.out.println("7) Ver mochila de entrenador");
			System.out.println("8) Retar gimnasio");
			System.out.println("9) Comprar objetos");
			System.out.println("10) Mostrar entrenadores detallado");
			System.out.println("11) Datos pokédex");
			System.out.println("X) Salir");

			System.out.println("\n====================================");

			opcion = sc.nextLine();

			switch (opcion) {

			default:
				System.out.println("Opción inválida, elige otra vez");
				break;

			case "1":
				crearEntrenador();
				break;

			case "2":
				eliminarEntrenador();
				break;

			case "3":
				modificarEntrenador();

			}

		} while (!opcion.toLowerCase().equals("x"));

		System.out.println("Saliendo...");

	}

	public static void crearEntrenador() {
		String nombre;

		System.out.print("Introduce el nombre del entrenador: ");
		nombre = sc.nextLine();

		Entrenador entrenador = new Entrenador(nombre);

		GestionEntrenadores.aniadirEntrenador(entrenador);
	}

	public static void eliminarEntrenador() {
		int id;

		if (GestionEntrenadores.entrenadores.size() == 0) {
			System.out.println("No hay entrenadores");
		} else {

			System.out.print("Introduce el id del entrenador a eliminar: ");
			id = sc.nextInt();
			sc.nextLine();

			GestionEntrenadores.eliminarEntrenadorPorID(id);
		}
	}

	public static void modificarEntrenador() {
		int id;

		if (GestionEntrenadores.entrenadores.size() == 0) {
			System.out.println("No hay entrenadores");
		} else {
			System.out.print("Introduce el id del entrenador a modificar: ");
			id = sc.nextInt();
			sc.nextLine();

			Entrenador entrenador = GestionEntrenadores.buscarEntrenadorPorID(id);

			if (entrenador != null) {
				GestionEntrenadores.modificarEntrenador(entrenador);
			} else {
				System.out.println("No se encontró ningún entrenador con el ID especificado.");
			}
		}

	}

}
