package game;

import java.util.Scanner;

public class App {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Variables
		String opcion = "";

		// Inicializar hashmap
		GestionGimnasios.pregunstasRespuestasInit();

		do {

			System.out.println("\n========= POKEMON TRAINERS =========\n");

			System.out.println("1) Entrenadores");
			System.out.println("2) Equipos");
			System.out.println("3) Lista de Entrenadores");
			System.out.println("4) Comprar Objetos");
			System.out.println("5) Retar Gimnasio");
			System.out.println("6) Ver Estuche");
			System.out.println("7) Ver Mochila");
			System.out.println("8) Pokédex");
			System.out.println("X) Salir");

			System.out.println("\n====================================\n");

			opcion = sc.nextLine().trim();

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

				opcion = sc.nextLine().trim();

				switch (opcion) {
				default:
					System.out.println("Opción inválida, elige otra vez");
					break;

				case "1":
					Entrenador.crearEntrenador();
					break;

				case "2":
					GestionEntrenadores.eliminarEntrenadorPorID();
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
					GestionListas.imprimirEntrenadores();
					break;

				case "2":
					GestionListas.imprimirEntrenadorPorID();
					break;

				case "3":
					GestionListas.imprimirEntrenadoresPorCantidadObjetos();
					break;

				case "4":
					GestionListas.imprimirEntrenadoresPorCantidadMedallas();
					break;

				case "5":
					GestionListas.imprimirEntrenadorNumPokemon();
					break;

				case "6":
					GestionListas.imprimirEntrenadorPorTipo();
					break;

				case "7":
					GestionListas.imprimirEntrenadoresPorPopularidadEquipo();
					break;

				case "8":
					GestionListas.imprimirEntrenadoresPorPesoEquipo();
					break;

				case "9":
					GestionListas.imprimirEntrenadoresPorAlturaEquipo();
					break;

				case "X", "x":
					opcion = "0";
					break;
				}

				break;

			case "4":
				int idEntrenadorElegido = GestionEntrenadores.elegirEntrenador();

				if (idEntrenadorElegido != -1) {
					GestionEntrenadores.idEntrenador = idEntrenadorElegido;

					System.out.println("\n== TIENDA =========================\n");
					GestionObjetos.imprimirTienda();
					System.out.println("\n====================================\n");
					GestionObjetos.comprarTienda();
				} else {
					System.out.println("No se seleccionó un entrenador válido.");
				}
				break;

			case "5":
				GestionGimnasios.retarGimnasio();
				break;

			case "6":
				GestionGimnasios.mostrarEstuche(GestionEntrenadores.elegirEntrenador());
				break;

			case "7":
				GestionObjetos.mostrarMochila(GestionEntrenadores.elegirEntrenador());
				break;

			case "8":
				GestionPokemon.elegirPokemon();
				break;

			case "X", "x":
				break;
			}

		} while (!opcion.equalsIgnoreCase("x"));

		System.out.println("Saliendo...");
		

	}

}
