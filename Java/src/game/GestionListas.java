package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionListas {

	static Scanner sc = new Scanner(System.in);
	public static final String AMARILLO = "\u001B[33m";
	public static final String RESET = "\u001B[0m";

	private static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	private static String user = "root";
	private static String password = "usuariodam";

	private static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadores() {
		String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
				+ "COUNT(M.idObjeto) AS cantidadObjetos, " + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
				+ "FROM EquipoContienePokemons EC " + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
				+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
				+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
				+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') " + "FROM Estuche ES "
				+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
				+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas " + "FROM Entrenador E "
				+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador " + "GROUP BY E.idEntrenador";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				System.out.println("No hay entrenadores registrados.");
				return;
			}

			do {
				int idEntrenador = rs.getInt("idEntrenador");
				String nombreEntrenador = rs.getString("nombreEntrenador");
				String fechaCreacion = rs.getString("fechaCreacion");
				int cantidadObjetos = rs.getInt("cantidadObjetos");
				String nombrePokemon = rs.getString("nombrePokemon");
				String nombreMedallas = rs.getString("nombreMedallas");

				fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
						nombreMedallas);

			} while (rs.next());

		} catch (SQLException e) {
			System.out.println("Error al imprimir los entrenadores: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadorPorID() {
		int idEntrenador;

		try {
			System.out.println("Lista de Entrenadores:");
			String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
			try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
				ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
				while (rsEntrenadores.next()) {
					System.out.println(rsEntrenadores.getInt("idEntrenador") + ". "
							+ rsEntrenadores.getString("nombreEntrenador"));
				}
			} catch (SQLException e) {
				e.getMessage();
			}

			System.out.print("Introduce el ID del entrenador: ");
			idEntrenador = sc.nextInt();
			sc.nextLine();

			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
					+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas " + "FROM Entrenador E "
					+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador " + "WHERE E.idEntrenador = ? "
					+ "GROUP BY E.idEntrenador";

			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				stmt.setInt(1, idEntrenador);
				ResultSet rs = stmt.executeQuery();

				if (!rs.next()) {
					System.out.println("No se encontró ningún entrenador con el ID especificado.");
					return;
				}

				int idEntrenadorResult = rs.getInt("idEntrenador");
				String nombreEntrenador = rs.getString("nombreEntrenador");
				String fechaCreacion = rs.getString("fechaCreacion");
				int cantidadObjetos = rs.getInt("cantidadObjetos");
				String nombrePokemon = rs.getString("nombrePokemon");
				String nombreMedallas = rs.getString("nombreMedallas");

				fichaEntrenador(idEntrenadorResult, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
						nombreMedallas);

			} catch (SQLException e) {
				System.out.println("Error al imprimir al entrenador: " + e.getMessage());
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Se esperaba un ID de entrenador válido.");
		}
	}

	public static void imprimirEntrenadorNumPokemon() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(P.idPokemon) AS cantidadPokemon, " + "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
					+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas " + "FROM Entrenador E "
					+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
					+ "LEFT JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
					+ "LEFT JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
					+ "LEFT JOIN Pokemon P ON ECP.idPokemon = P.idPokemon " + "GROUP BY E.idEntrenador "
					+ "ORDER BY cantidadPokemon DESC";

			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				ResultSet rs = stmt.executeQuery();

				if (!rs.next()) {
					System.out.println("No hay entrenadores registrados.");
					return;
				}

				do {
					int idEntrenador = rs.getInt("idEntrenador");
					String nombreEntrenador = rs.getString("nombreEntrenador");
					String fechaCreacion = rs.getString("fechaCreacion");
					int cantidadObjetos = rs.getInt("cantidadObjetos");
					String nombrePokemon = rs.getString("nombrePokemon");
					String nombreMedallas = rs.getString("nombreMedallas");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);

				} while (rs.next());

			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadorPorTipo() {
		String tipoEspecifico;

		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(P.idPokemon) AS cantidadPokemon, " + "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ', ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo " + "WHERE EQ.idEntrenador = E.idEntrenador "
					+ "AND (P.tipoPrimario = ? OR P.tipoSecundario = ?)) AS nombrePokemonConTipo, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
					+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon " + "FROM Entrenador E "
					+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
					+ "LEFT JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
					+ "LEFT JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
					+ "LEFT JOIN Pokemon P ON ECP.idPokemon = P.idPokemon " + "GROUP BY E.idEntrenador "
					+ "HAVING nombrePokemonConTipo IS NOT NULL";

			System.out.print("Introduce el tipo específico: ");
			tipoEspecifico = sc.nextLine();

			try (PreparedStatement stmt = connection.prepareStatement(query)) {
				stmt.setString(1, tipoEspecifico);
				stmt.setString(2, tipoEspecifico);
				ResultSet rs = stmt.executeQuery();

				if (!rs.next()) {
					System.out.println("No hay entrenadores que tengan un Pokémon de tipo " + tipoEspecifico);
					return;
				}

				do {
					int idEntrenador = rs.getInt("idEntrenador");
					String nombreEntrenador = rs.getString("nombreEntrenador");
					String fechaCreacion = rs.getString("fechaCreacion");
					int cantidadPokemon = rs.getInt("cantidadPokemon");
					int cantidadObjetos = rs.getInt("cantidadObjetos");
					String nombrePokemon = rs.getString("nombrePokemon");
					String nombrePokemonConTipo = rs.getString("nombrePokemonConTipo");
					String nombreMedallas = rs.getString("nombreMedallas");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);

					if (cantidadPokemon == 1) {
						System.out.println(nombrePokemonConTipo + " es de tipo " + tipoEspecifico);
					} else {
						System.out.println(nombrePokemonConTipo + " son de tipo " + tipoEspecifico);
					}
					

				} while (rs.next());

			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por tipo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadoresPorPopularidadEquipo() {
	    try {
	        String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
	                + "COUNT(M.idObjeto) AS cantidadObjetos, "
	                + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
	                + "FROM EquipoContienePokemons EC "
	                + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
	                + "WHERE EC.idEquipo = EQ.idEquipo) AS nombrePokemon, "
	                + "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') "
	                + "FROM Estuche ES "
	                + "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
	                + "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
	                + "SUM(P.popularidad) AS popularidadTotal "
	                + "FROM Entrenador E "
	                + "INNER JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
	                + "INNER JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
	                + "INNER JOIN Pokemon P ON ECP.idPokemon = P.idPokemon "
	                + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
	                + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, EQ.idEquipo "
	                + "ORDER BY popularidadTotal ASC";

	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            ResultSet rs = stmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("No hay entrenadores registrados.");
	                return;
	            }

	            do {
	                int idEntrenador = rs.getInt("idEntrenador");
	                String nombreEntrenador = rs.getString("nombreEntrenador");
	                String fechaCreacion = rs.getString("fechaCreacion");
	                int cantidadObjetos = rs.getInt("cantidadObjetos");
	                String nombrePokemon = rs.getString("nombrePokemon");
	                String nombreMedallas = rs.getString("nombreMedallas");
	                int popularidadTotal = rs.getInt("popularidadTotal");

	                fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon, nombreMedallas);
	                System.out.println("Popularidad del Equipo: " + popularidadTotal + "#");

	            } while (rs.next());
	        } catch (SQLException e) {
	            System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	public static void imprimirEntrenadoresPorPesoEquipo() {
	    try {
	        String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
	                + "COUNT(M.idObjeto) AS cantidadObjetos, "
	                + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
	                + "FROM EquipoContienePokemons EC "
	                + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
	                + "WHERE EC.idEquipo = EQ.idEquipo) AS nombrePokemon, "
	                + "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') "
	                + "FROM Estuche ES "
	                + "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
	                + "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
	                + "SUM(P.peso) AS pesoTotal "
	                + "FROM Entrenador E "
	                + "INNER JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
	                + "INNER JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
	                + "INNER JOIN Pokemon P ON ECP.idPokemon = P.idPokemon "
	                + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
	                + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, EQ.idEquipo "
	                + "ORDER BY pesoTotal DESC";

	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            ResultSet rs = stmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("No hay entrenadores registrados.");
	                return;
	            }

	            do {
	                int idEntrenador = rs.getInt("idEntrenador");
	                String nombreEntrenador = rs.getString("nombreEntrenador");
	                String fechaCreacion = rs.getString("fechaCreacion");
	                int cantidadObjetos = rs.getInt("cantidadObjetos");
	                String nombrePokemon = rs.getString("nombrePokemon");
	                String nombreMedallas = rs.getString("nombreMedallas");
	                int pesoTotal = rs.getInt("pesoTotal");

	                fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon, nombreMedallas);
	                System.out.println("Peso del Equipo: " + pesoTotal + "kg");

	            } while (rs.next());
	        } catch (SQLException e) {
	            System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public static void imprimirEntrenadoresPorAlturaEquipo() {
	    try {
	        String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
	                + "COUNT(M.idObjeto) AS cantidadObjetos, "
	                + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
	                + "FROM EquipoContienePokemons EC "
	                + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
	                + "WHERE EC.idEquipo = EQ.idEquipo) AS nombrePokemon, "
	                + "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') "
	                + "FROM Estuche ES "
	                + "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
	                + "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
	                + "SUM(P.altura) AS alturaTotal "
	                + "FROM Entrenador E "
	                + "INNER JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
	                + "INNER JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
	                + "INNER JOIN Pokemon P ON ECP.idPokemon = P.idPokemon "
	                + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
	                + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, EQ.idEquipo "
	                + "ORDER BY alturaTotal DESC";

	        try (PreparedStatement stmt = connection.prepareStatement(query)) {
	            ResultSet rs = stmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("No hay entrenadores registrados.");
	                return;
	            }

	            do {
	                int idEntrenador = rs.getInt("idEntrenador");
	                String nombreEntrenador = rs.getString("nombreEntrenador");
	                String fechaCreacion = rs.getString("fechaCreacion");
	                int cantidadObjetos = rs.getInt("cantidadObjetos");
	                String nombrePokemon = rs.getString("nombrePokemon");
	                String nombreMedallas = rs.getString("nombreMedallas");
	                int alturaTotal = rs.getInt("alturaTotal");

	                fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon, nombreMedallas);
	                System.out.println("Altura del Equipo: " + alturaTotal + "m");

	            } while (rs.next());
	        } catch (SQLException e) {
	            System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	private static void fichaEntrenador(int idEntrenadorResult, String nombreEntrenador, String fechaCreacion,
			int cantidadObjetos, String nombrePokemon, String nombreMedalla) {

		System.out.println("\n==" + AMARILLO + " FICHA ENTRENADOR " + RESET + "============= Nº ID/"
				+ idEntrenadorResult + " ==\n" + "\n• NOMBRE / " + nombreEntrenador + "\n"
				+ "---------------------------\n" + "• MOCHILA:\t" + cantidadObjetos + " objetos\n" + "• FECHA:\t"
				+ fechaCreacion + "\n" + "• EQUIPO:\n  " + (nombrePokemon != null ? nombrePokemon : "") + "\n"
				+ "• MEDALLAS:\n" + (nombreMedalla != null ? nombreMedalla : "")
				+ "\n============================================");

	}

}
