package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionEntrenadores {

	static Scanner sc = new Scanner(System.in);
	static int idEntrenador;

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

	public static void aniadirEntrenador(Entrenador entrenador) {
		String query = "INSERT INTO Entrenador (nombreEntrenador, fechaCreacion) VALUES (?, NOW())";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, entrenador.getNombreEntrenador());
			preparedStatement.executeUpdate();
			System.out.println("Entrenador registrado correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al añadir entrenador: " + e.getMessage());
		}
	}

	public static void eliminarEntrenadorPorID() {
		int id;

		try {
			System.out.println("Lista de Entrenadores:");
			String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
			try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
				ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
				while (rsEntrenadores.next()) {
					System.out.println(rsEntrenadores.getInt("idEntrenador") + ". "
							+ rsEntrenadores.getString("nombreEntrenador"));
				}
			}

			System.out.print("Introduce el id del entrenador a eliminar: ");
			id = sc.nextInt();
			sc.nextLine();

			String deleteEquipoContienePokemonsQuery = "DELETE FROM EquipoContienePokemons WHERE idEquipo IN (SELECT idEquipo FROM Equipo WHERE idEntrenador = ?)";
			try (PreparedStatement deleteEquipoContienePokemonsStmt = connection
					.prepareStatement(deleteEquipoContienePokemonsQuery)) {
				deleteEquipoContienePokemonsStmt.setInt(1, id);
				deleteEquipoContienePokemonsStmt.executeUpdate();
			}

			String deleteEquiposQuery = "DELETE FROM Equipo WHERE idEntrenador = ?";
			try (PreparedStatement deleteEquiposStmt = connection.prepareStatement(deleteEquiposQuery)) {
				deleteEquiposStmt.setInt(1, id);
				deleteEquiposStmt.executeUpdate();
			}

			String deleteEntrenadorQuery = "DELETE FROM Entrenador WHERE idEntrenador = ?";
			try (PreparedStatement deleteEntrenadorStmt = connection.prepareStatement(deleteEntrenadorQuery)) {
				deleteEntrenadorStmt.setInt(1, id);
				int rowsAffected = deleteEntrenadorStmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Entrenador eliminado correctamente.");
				} else {
					System.out.println("No se encontró ningún entrenador con el ID especificado.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar al entrenador: " + e.getMessage());
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

			System.out.println("Lista de Entrenadores:");
			String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
			try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
				ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
				while (rsEntrenadores.next()) {
					System.out.println(rsEntrenadores.getInt("idEntrenador") + ". "
							+ rsEntrenadores.getString("nombreEntrenador"));
				}
			}

			System.out.print("Introduce el ID del entrenador al que deseas añadir el Pokémon: ");
			int idEntrenador = sc.nextInt();
			sc.nextLine();

			String selectEntrenador = "SELECT * FROM Entrenador WHERE idEntrenador = ?";
			try (PreparedStatement stmt = connection.prepareStatement(selectEntrenador)) {
				stmt.setInt(1, idEntrenador);
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
					preparedStatement.setInt(2, idEntrenador);
					int rowsAffected = preparedStatement.executeUpdate();

					if (rowsAffected > 0) {
						System.out.println("Entrenador modificado correctamente.");
					} else {
						System.out.println("No se pudo modificar el entrenador.");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al modificar el entrenador: " + e.getMessage());
		}
	}

	public static void elegirEntrenador() {

		System.out.println("Lista de Entrenadores:");
		String queryEntrenadores = "SELECT idEntrenador, nombreEntrenador FROM Entrenador";
		try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
			ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
			while (rsEntrenadores.next()) {
				System.out.println(
						rsEntrenadores.getInt("idEntrenador") + ". " + rsEntrenadores.getString("nombreEntrenador"));
			}
		} catch (SQLException e) {
			e.getMessage();
		}

		System.out.print("Introduce el id del entrenador: ");

		idEntrenador = sc.nextInt();
	}

}
