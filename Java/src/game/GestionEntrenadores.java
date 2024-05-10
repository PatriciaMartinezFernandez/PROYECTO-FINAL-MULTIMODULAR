package game;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionEntrenadores {

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
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void imprimirEntrenadores() {
		String query = "SELECT E.idEntrenador, E.nombreEntrenador, E.fechaCreacion, "
				+ "COUNT(M.idObjeto) AS cantidadObjetos, " + "(SELECT GROUP_CONCAT(P.nombrePokemon SEPARATOR ', ') "
				+ "FROM EquipoContienePokemons EC INNER JOIN Pokemon P ON EC.idPokemon = P.idPokemon "
				+ "WHERE EC.idEquipo = E.idEntrenador) AS nombrePokemon, "
				+ "(SELECT GROUP_CONCAT(MD.nombre SEPARATOR ', ') "
				+ "FROM Estuche ES INNER JOIN Medalla MD ON ES.idMedalla = MD.idMedalla "
				+ "WHERE ES.idEntrenador = E.idEntrenador) AS nombreMedallas "
				+ "FROM Entrenador E LEFT JOIN Mochila M ON E.idEntrenador = M.idEntrenador "
				+ "GROUP BY E.idEntrenador";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				System.out.println("No hay entrenadores registrados.");
				return;
			}

			do {
				int idEntrenador = rs.getInt("idEntrenador");
				String nombreEntrenador = rs.getString("nombreEntrenador");
				Date fechaCreacion = rs.getDate("fechaCreacion");
				int cantidadObjetos = rs.getInt("cantidadObjetos");
				String nombrePokemon = rs.getString("nombrePokemon");
				String nombreMedallas = rs.getString("nombreMedallas");

				System.out.println(
						"\n==" + AMARILLO + " FICHA ENTRENADOR " + RESET + "============= Nº ID/" + idEntrenador
								+ " ==\n" + "\n• NOMBRE / " + nombreEntrenador + "\n" + "---------------------------\n"
								+ "• MOCHILA:\t" + cantidadObjetos + " objetos\n" + "• FECHA:\t" + fechaCreacion + "\n"
								+ "• EQUIPO:\n" + (nombrePokemon != null ? nombrePokemon : "") + "\n" + "• MEDALLAS:\n"
								+ (nombreMedallas != null ? nombreMedallas : "")
								+ "\n============================================");

			} while (rs.next());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void aniadirEntrenador(Entrenador entrenador) {
		String query = "INSERT INTO Entrenador (nombreEntrenador, fechaCreacion) VALUES (?, NOW())";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, entrenador.getNombreEntrenador());
			preparedStatement.executeUpdate();
			System.out.println("Entrenador registrado correctamente.");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void eliminarEntrenadorPorID(int id) {
		String query = "DELETE FROM Entrenador WHERE idEntrenador = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Entrenador eliminado correctamente.");
			} else {
				System.out.println("No se encontró ningún entrenador con el ID especificado.");
			}

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}

	public static void modificarEntrenador() {
		String queryCount = "SELECT COUNT(*) AS total FROM Entrenador";

		try (PreparedStatement stmtContar = connection.prepareStatement(queryCount)) {
			ResultSet rs = stmtContar.executeQuery();
			rs.next();
			int totalEntrenadores = rs.getInt("total");

			if (totalEntrenadores == 0) {
				System.out.println("No hay entrenadores registrados.");
				return;
			}

			System.out.print("Introduce el id del entrenador a modificar: ");
			int id = sc.nextInt();
			sc.nextLine();

			String selectEntrenador = "SELECT * FROM Entrenador WHERE idEntrenador = ?";
			try (PreparedStatement stmt = connection.prepareStatement(selectEntrenador)) {
				stmt.setInt(1, id);
				ResultSet rsEntrenador = stmt.executeQuery();

				if (!rsEntrenador.next()) {
					System.out.println("No se encontró ningún entrenador con el ID especificado.");
					return;
				}

				String nombreActual = rsEntrenador.getString("nombreEntrenador");

				System.out.print("Introduce un nuevo nombre para este entrenador (" + nombreActual + "): ");
				String nuevoNombre = sc.nextLine().trim();

				if (nuevoNombre.isEmpty()) {
					nuevoNombre = nombreActual;
				}

				String modificarEntrenador = "UPDATE Entrenador SET nombreEntrenador = ? WHERE idEntrenador = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(modificarEntrenador)) {
					preparedStatement.setString(1, nuevoNombre);
					preparedStatement.setInt(2, id);
					int rowsAffected = preparedStatement.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("Entrenador modificado correctamente.");
					} else {
						System.out.println("No se pudo modificar el entrenador.");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	}
}
