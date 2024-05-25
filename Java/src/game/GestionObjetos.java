package game;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase GestionObjetos proporciona métodos para gestionar los objetos dentro
 * del juego. Incluyendo la visualización de la tienda, la compra de objetos y
 * la visualización de la mochila del jugador.
 * 
 * @author Jaime Martínez Fernández
 */

public class GestionObjetos extends Gestion {

	/**
	 * Este método realiza una consulta a la vista de la tienda en la base de datos
	 * y si ocurre un error de SQL imprime el mensaje.
	 */

	public static void imprimirTienda() {
		try {
			String query = "SELECT * FROM VistaTienda";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("idObjeto") + ". " + resultSet.getString("nombreObjeto"));
			}

		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
		}
	}

	/**
	 * Permite al jugador comprar un objeto de la tienda. Solicita al jugador que
	 * introduzca el ID del objeto y la cantidad. Si ocurre un error de SQL imprime
	 * el mensaje.
	 */

	public static void comprarTienda() {
		System.out.print("Introduce el número del objeto que quieres comprar: ");
		int opcionObjeto = sc.nextInt();
		sc.nextLine();

		System.out.print("¿Cuántos deseas comprar?: ");
		int cantidad = sc.nextInt();
		sc.nextLine();

		try {
			String callProcedure = "{CALL ComprarTienda(?, ?, ?)}";
			CallableStatement cstmt = connection.prepareCall(callProcedure);
			cstmt.setInt(1, GestionEntrenadores.idEntrenador);
			cstmt.setInt(2, opcionObjeto);
			cstmt.setInt(3, cantidad);
			cstmt.execute();
			System.out.println("¡Objeto añadido a la mochila del entrenador!");

		} catch (SQLException e) {
			System.out.println("Error en la consulta SQL: " + e.getMessage());
		}
	}

	/**
	 * Muestra los objetos y sus cantidades en la mochila del entrenador
	 * especificado. Si ocurre un error de SQL imprime el mensaje.
	 * 
	 * @param idEntrenador ID del entrenador cuya mochila se va a mostrar.
	 */

	public static void mostrarMochila(int idEntrenador) {
		System.out.println("\n== MOCHILA ====================== Nº ID/" + idEntrenador + " ==\n");

		try {
			String callMostrarMochila = "{CALL MostrarMochila(?)}";
			CallableStatement cstmt = connection.prepareCall(callMostrarMochila);
			cstmt.setInt(1, idEntrenador);
			ResultSet resultSet = cstmt.executeQuery();

			while (resultSet.next()) {
				String nombreObjeto = resultSet.getString("nombreObjeto");
				int cantidad = resultSet.getInt("cantidad");
				System.out.println(cantidad + "x\t" + nombreObjeto);
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener la mochila: " + e.getMessage());
		}

		System.out.println("\n============================================");
	}
}
