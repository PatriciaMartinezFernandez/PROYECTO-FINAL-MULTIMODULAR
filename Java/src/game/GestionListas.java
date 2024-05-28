package game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

/**
 * La clase GestionListas proporciona métodos para mostrar información sobre los
 * entrenadores.
 * 
 * @author Jaime Martínez Fernández
 */

public class GestionListas extends Gestion {

	/**
	 * Imprime la información de todos los usuarios, incluyendo sus objetos, sus
	 * Pokémons y sus medallas.
	 */

	public static void imprimirEntrenadores() {
		String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
				+ "COUNT(M.idObjeto) AS cantidadObjetos, " + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
				+ "FROM EquipoContienePokemons EC " + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
				+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
				+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
				+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
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

	/**
	 * Imprime la información de un entenador especifico.
	 */

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
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
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

	/**
	 * Imprime la información de los entrenadores ordenados por la cantidad de
	 * Pokémons que hay en su equipo.
	 */

	public static void imprimirEntrenadorNumPokemon() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
		             + "COUNT(DISTINCT P.idPokemon) AS cantidadPokemon, "
		             + "COUNT(DISTINCT M.idObjeto) AS cantidadObjetos, "
		             + "(SELECT GROUP_CONCAT(DISTINCT P.nombrePokemon SEPARATOR ' | ') "
		             + "FROM EquipoContienePokemons EC "
		             + "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
		             + "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
		             + "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
		             + "(SELECT GROUP_CONCAT(DISTINCT MD.nombre SEPARATOR ' | ') "
		             + "FROM Estuche ES "
		             + "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
		             + "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas "
		             + "FROM Entrenador E "
		             + "LEFT JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
		             + "LEFT JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
		             + "LEFT JOIN Pokemon P ON ECP.idPokemon = P.idPokemon "
		             + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
		             + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion "
		             + "ORDER BY cantidadPokemon DESC;";

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

	/**
	 * Imprime la información del entrendores que tienen Pokémons de un tipo
	 * especifico.
	 */

	public static void imprimirEntrenadorPorTipo() {
		String tipoEspecifico;

		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(P.idPokemon) AS cantidadPokemon, " + "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ', ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo " + "WHERE EQ.idEntrenador = E.idEntrenador "
					+ "AND (P.tipoPrimario = ? OR P.tipoSecundario = ?)) AS nombrePokemonConTipo, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
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
					int cantidadObjetos = rs.getInt("cantidadObjetos");
					String nombrePokemon = rs.getString("nombrePokemon");
					String nombrePokemonConTipo = rs.getString("nombrePokemonConTipo");
					String nombreMedallas = rs.getString("nombreMedallas");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);

					System.out.println("De tipo " + tipoEspecifico + ": " + nombrePokemonConTipo);

				} while (rs.next());

			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por tipo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la información de los entrenadores ordenados por la popularidad
	 * promedia de su equipo.
	 */

	public static void imprimirEntrenadoresPorPopularidadEquipo() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "WHERE EC.idEquipo = EQ.idEquipo) AS nombrePokemon, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
					+ "SUM(P.popularidad) AS popularidadTotal, "
					+ "(SUM(P.popularidad) / COUNT(DISTINCT ECP.idPokemon)) AS popularidadPromedioPorPokemon "
					+ "FROM Entrenador E " + "INNER JOIN Equipo EQ ON E.idEntrenador = EQ.idEntrenador "
					+ "INNER JOIN EquipoContienePokemons ECP ON EQ.idEquipo = ECP.idEquipo "
					+ "INNER JOIN Pokemon P ON ECP.idPokemon = P.idPokemon "
					+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
					+ "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, EQ.idEquipo "
					+ "ORDER BY popularidadPromedioPorPokemon ASC";

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
					int popularidadPromedioPorPokemon = rs.getInt("popularidadPromedioPorPokemon");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);
					System.out.println("Popularidad del Equipo: " + popularidadPromedioPorPokemon + "#");

				} while (rs.next());
			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la información de los entrenadores ordenados por el peso total de su
	 * equipo.
	 */

	public static void imprimirEntrenadoresPorPesoEquipo() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
		             + "COALESCE(COUNT(DISTINCT M.idObjeto), 0) AS cantidadObjetos, "
		             + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
		             + " FROM EquipoContienePokemons EC "
		             + " INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
		             + " WHERE EC.idEquipo IN (SELECT EQ.idEquipo FROM Equipo EQ WHERE EQ.idEntrenador = E.idEntrenador)) AS nombrePokemon, "
		             + "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') "
		             + " FROM Estuche ES "
		             + " INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
		             + " WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
		             + "(SELECT SUM(P2.peso) "
		             + " FROM EquipoContienePokemons EC2 "
		             + " INNER JOIN Pokemon P2 ON EC2.idPokemon = P2.idPokemon "
		             + " WHERE EC2.idEquipo IN (SELECT EQ2.idEquipo FROM Equipo EQ2 WHERE EQ2.idEntrenador = E.idEntrenador)) AS pesoTotal "
		             + "FROM Entrenador E "
		             + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
		             + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion "
		             + "ORDER BY pesoTotal DESC;";

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
					double pesoTotal = rs.getDouble("pesoTotal");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);
					System.out.println("Peso del Equipo: " + pesoTotal + "kg");

				} while (rs.next());
			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la información de los entrenadores ordenados por la altura total de
	 * su equipo.
	 */

	public static void imprimirEntrenadoresPorAlturaEquipo() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
		             + "COALESCE(COUNT(DISTINCT M.idObjeto), 0) AS cantidadObjetos, "
		             + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') "
		             + " FROM EquipoContienePokemons EC "
		             + " INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
		             + " WHERE EC.idEquipo IN (SELECT EQ.idEquipo FROM Equipo EQ WHERE EQ.idEntrenador = E.idEntrenador)) AS nombrePokemon, "
		             + "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') "
		             + " FROM Estuche ES "
		             + " INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
		             + " WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas, "
		             + "(SELECT SUM(P2.altura) "
		             + " FROM EquipoContienePokemons EC2 "
		             + " INNER JOIN Pokemon P2 ON EC2.idPokemon = P2.idPokemon "
		             + " WHERE EC2.idEquipo IN (SELECT EQ2.idEquipo FROM Equipo EQ2 WHERE EQ2.idEntrenador = E.idEntrenador)) AS alturaTotal "
		             + "FROM Entrenador E "
		             + "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
		             + "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion "
		             + "ORDER BY alturaTotal DESC;";

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
					double alturaTotal = rs.getDouble("alturaTotal");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadObjetos, nombrePokemon,
							nombreMedallas);
					System.out.println("Altura del Equipo: " + alturaTotal + "m");

				} while (rs.next());
			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por popularidad de equipo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la información de los entrenadores ordenados por la cantidad de
	 * objetos en su mochila.
	 */

	public static void imprimirEntrenadoresPorCantidadObjetos() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "COUNT(M.idObjeto) AS cantidadObjetos, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
					+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas " + "FROM Entrenador E "
					+ "LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
					+ "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion " + "ORDER BY cantidadObjetos DESC";

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
				System.out.println("Error al imprimir los entrenadores por cantidad de objetos: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la información de los entrenadores ordenados por la cantidad de
	 * medallas obtenidas.
	 */

	public static void imprimirEntrenadoresPorCantidadMedallas() {
		try {
			String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
					+ "(SELECT COUNT(*) FROM Estuche ES WHERE ES.idEntrenador = E.idEntrenador) AS cantidadMedallas, "
					+ "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ' | ') " + "FROM EquipoContienePokemons EC "
					+ "INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
					+ "INNER JOIN Equipo EQ ON EC.idEquipo = EQ.idEquipo "
					+ "WHERE EQ.idEntrenador = E.idEntrenador) AS nombrePokemon, "
					+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ' | ') " + "FROM Estuche ES "
					+ "INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
					+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas " + "FROM Entrenador E "
					+ "GROUP BY E.idEntrenador, E.nombreEntrenador, E.fechaCreacion "
					+ "ORDER BY cantidadMedallas DESC";

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
					int cantidadMedallas = rs.getInt("cantidadMedallas");
					String nombrePokemon = rs.getString("nombrePokemon");
					String nombreMedallas = rs.getString("nombreMedallas");

					fichaEntrenador(idEntrenador, nombreEntrenador, fechaCreacion, cantidadMedallas, nombrePokemon,
							nombreMedallas);

				} while (rs.next());
			} catch (SQLException e) {
				System.out.println("Error al imprimir los entrenadores por cantidad de medallas: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Imprime la ficha de un entrenador con la información proporcionada.
	 *
	 * @param idEntrenador     ID del entrenador.
	 * @param nombreEntrenador Nombre del entrenador.
	 * @param fechaCreacion    Fecha de creación del entrenador.
	 * @param cantidadObjetos  Cantidad de objetos en la mochila del entrenador.
	 * @param nombrePokemon    Nombres de los Pokémon en el equipo del entrenador.
	 * @param nombreMedalla    Nombres de las medallas obtenidas por el entrenador.
	 */

	private static void fichaEntrenador(int idEntrenador, String nombreEntrenador, String fechaCreacion,
			int cantidadObjetos, String nombrePokemon, String nombreMedalla) {

		System.out.println("\n==" + AMARILLO + " FICHA ENTRENADOR " + RESET + "============= Nº ID/" + idEntrenador
				+ " ==\n" + "\n• NOMBRE / " + nombreEntrenador + "\n" + "--------------------------------------\n"
				+ "• MOCHILA:\t" + cantidadObjetos + " objetos\n" + "• FECHA:\t" + fechaCreacion + "\n"
				+ "• EQUIPO:\n  " + (nombrePokemon != null ? nombrePokemon : "") + "\n" + "• MEDALLAS:\n  "
				+ (nombreMedalla != null ? nombreMedalla : "") + "\n============================================");

	}

}