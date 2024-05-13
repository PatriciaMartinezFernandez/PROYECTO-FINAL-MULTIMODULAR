package game;

import java.util.Scanner;

public class App {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Variables
		String opcion = "";

		do {

			System.out.println("\n========= POKEMON TRAINERS =========\n");

			System.out.println("1) Entrenadores");
			System.out.println("2) Equipos");
			System.out.println("3) Lista de Entrenadores");
			System.out.println("4) Comprar Objetos");
			System.out.println("5) Retar Gimnasio");
			System.out.println("6) Ver Estuche");
			System.out.println("7) Ver Equipo");
			System.out.println("8) Ver Mochila");
			System.out.println("9) Pokédex");
			System.out.println("X) Salir");

			System.out.println("\n====================================\n");

			opcion = sc.nextLine();

			switch (opcion) {

			default:
				System.out.println("Opción inválida, elige otra vez");
				break;

			case "1":

				System.out.println("\n== ENTRENADORES ====================\n");

				System.out.println("1) Registrar entrenador");
				System.out.println("2) Eliminar entrenador");
				System.out.println("3) Modificar entrenador");
				System.out.println("X) Salir");

				System.out.println("\n====================================\n");

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
					GestionEntrenadores.modificarEntrenador();
					break;

				case "X", "x":
					opcion = "0";
					break;
				}

				break;

			case "2":
				
				System.out.println("\n== EQUIPOS =========================\n");

				System.out.println("1) Añadir Pokémon a un Equipo");
				System.out.println("2) Quitar Pokémon de un Equipo");
				System.out.println("X) Salir");

				System.out.println("\n====================================\n");

				opcion = sc.nextLine();

				switch (opcion) {
				default:
					System.out.println("Opción inválida, elige otra vez");
					break;

				case "1":
					GestionEquipos.aniadirPokemon();
					break;

				case "2":
					GestionEquipos.eliminarPokemon();
					break;

				case "X", "x":
					opcion = "0";
					break;
				}
				
				break;

			case "3":
				System.out.println("\n== LISTADOS ========================\n");

				System.out.println("1) Entrenadores desordenados");
				System.out.println("2) Entrenador específico");
				System.out.println("3) Por cantidad de objetos");
				System.out.println("4) Por cantidad de medallas");
				System.out.println("5) Por número de Pokémon en equipo");
				System.out.println("6) Por tipo de Pokémon en equipo");
				System.out.println("7) Por popularidad del equipo");
				System.out.println("8) Por peso total del equipo");
				System.out.println("9) Por altura total del equipo");
				System.out.println("X) Salir");

				System.out.println("\n====================================\n");

				opcion = sc.nextLine();

				switch (opcion) {
				default:
					System.out.println("Opción inválida, elige otra vez");
					break;

				case "1":
					GestionEntrenadores.imprimirEntrenadores();
					break;

				case "2":
					break;

				case "3":
					break;

				case "X", "x":
					opcion = "0";
					break;
				}

				break;

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

		System.out.print("Introduce el id del entrenador a eliminar: ");
		id = sc.nextInt();
		sc.nextLine();

		GestionEntrenadores.eliminarEntrenadorPorID(id);
	}

}
