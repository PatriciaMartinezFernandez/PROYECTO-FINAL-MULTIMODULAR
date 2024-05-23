package game;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionObjetos extends Gestion {

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
