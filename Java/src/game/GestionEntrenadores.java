package game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

/**
 * La clase GestionEntrenadores proporciona métodos estáticos para gestionar la
 * información relacionada con los entrenadores en el juego. Esto incluye
 * añadir, eliminar, modificar y elegir entrenadores.
 * 
 * Esta clase extiende de la clase Gestion para utilizar la conexión a la base de datos.
 * 
 * @author Jaime Martínez Fernández
 */

public class GestionEntrenadores extends Gestion {

	static int idEntrenador;

	/**
	 * Añade un nuevo entrenador a la base de datos.
	 * 
	 * @param entrenador Entrenador a añadir
	 * @throws IllegalArgumentException Si el nombre del entrenador es nulo o vacío
	 */
	
	public static void aniadirEntrenador(Entrenador entrenador) throws IllegalArgumentException {
		if (entrenador == null || entrenador.getNombreEntrenador() == null
				|| entrenador.getNombreEntrenador().isEmpty()) {
			throw new IllegalArgumentException("El nombre del entrenador no puede estar vacío");
		}

		String query = "{CALL aniadirEntrenador(?)}";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, entrenador.getNombreEntrenador());
			preparedStatement.executeUpdate();
			System.out.println("Entrenador registrado correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al añadir entrenador: " + e.getMessage());
		}
	}

	/**
	 * Elimina un entrenador de la base de datos según su ID.
	 */
	
	public static void eliminarEntrenadorPorID() {
		try {
			idEntrenador = elegirEntrenador();

			String query = "{CALL eliminarEntrenadorPorID(?)}";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, idEntrenador);
				int rowsAffected = preparedStatement.executeUpdate();

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

	/**
	 * Modifica el nombre de un entrenador en la base de datos.
	 */
	
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

			idEntrenador = elegirEntrenador();

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

				String modificarEntrenador = "{CALL modificarEntrenador(?, ?)}";
				try (PreparedStatement preparedStatement = connection.prepareStatement(modificarEntrenador)) {
					preparedStatement.setInt(1, idEntrenador);
					preparedStatement.setString(2, nuevoNombre);
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

	/**
	 * Permite al usuario elegir un entrenador de una lista que se muestra en consola.
	 * 
	 * @return ID del entrenador elegido, -1 si no se pudo elegir
	 */
	
	public static int elegirEntrenador() {
		System.out.println("Lista de Entrenadores:");
		String queryEntrenadores = "SELECT * FROM VistaEntrenadores";
		boolean hayEntrenadores = false;

		try (PreparedStatement stmtEntrenadores = connection.prepareStatement(queryEntrenadores)) {
			ResultSet rsEntrenadores = stmtEntrenadores.executeQuery();
			while (rsEntrenadores.next()) {
				hayEntrenadores = true;
				System.out.println(
						rsEntrenadores.getInt("idEntrenador") + ". " + rsEntrenadores.getString("nombreEntrenador"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		if (!hayEntrenadores) {
			System.out.println("No hay entrenadores disponibles.");
			return -1;
		}

		int idEntrenador = 0;

		try {
			System.out.print("Introduce el id del entrenador: ");
			idEntrenador = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Error: Por favor, introduce un valor entero válido.");
			sc.nextLine();
		}

		return idEntrenador;
	}

}
