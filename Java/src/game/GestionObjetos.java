package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestionObjetos {

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


	public static void imprimirTienda() {
	    try {
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT idObjeto, nombreObjeto FROM Objeto ORDER BY idObjeto ASC");
	        while (resultSet.next()) {
	            System.out.println(resultSet.getInt("idObjeto") + ". " + resultSet.getString("nombreObjeto"));
	        }

	    } catch (SQLException e) {
	        e.getMessage();
	    }
	}


	public static void comprarTienda(int idEntrenador) {
	    System.out.print("Introduce el número del objeto que quieres comprar: ");
	    int opcionObjeto = sc.nextInt();
	    sc.nextLine();

	    System.out.print("¿Cuántos deseas comprar?: ");
	    int cantidad = sc.nextInt();
	    sc.nextLine();

	    try {
	        String querySelect = "SELECT cantidad FROM Mochila WHERE idEntrenador = ? AND idObjeto = ?";
	        try (PreparedStatement stmtSelect = connection.prepareStatement(querySelect)) {
	            stmtSelect.setInt(1, idEntrenador);
	            stmtSelect.setInt(2, opcionObjeto);
	            ResultSet rs = stmtSelect.executeQuery();

	            if (rs.next()) {
	                int cantidadExistente = rs.getInt("cantidad");
	                cantidad += cantidadExistente;

	                String queryUpdate = "UPDATE Mochila SET cantidad = ? WHERE idEntrenador = ? AND idObjeto = ?";
	                try (PreparedStatement stmtUpdate = connection.prepareStatement(queryUpdate)) {
	                    stmtUpdate.setInt(1, cantidad);
	                    stmtUpdate.setInt(2, idEntrenador);
	                    stmtUpdate.setInt(3, opcionObjeto);
	                    int filasActualizadas = stmtUpdate.executeUpdate();
	                    if (filasActualizadas > 0) {
	                        System.out.println("¡Objeto añadido a la mochila del entrenador!");
	                    } else {
	                        System.out.println("Error al actualizar la cantidad del objeto en la mochila.");
	                    }
	                }
	            } else {
	                String queryInsert = "INSERT INTO Mochila (idEntrenador, idObjeto, cantidad) VALUES (?, ?, ?)";
	                try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
	                    stmtInsert.setInt(1, idEntrenador);
	                    stmtInsert.setInt(2, opcionObjeto);
	                    stmtInsert.setInt(3, cantidad);
	                    int filasInsertadas = stmtInsert.executeUpdate();
	                    if (filasInsertadas > 0) {
	                        System.out.println("¡Objeto añadido a la mochila del entrenador!");
	                    } else {
	                        System.out.println("Error al añadir el objeto a la mochila.");
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en la consulta SQL: " + e.getMessage());
	    }
	}


	public static void mostrarMochila(int idEntrenador) {
	    System.out.println("\n==" + AMARILLO + " MOCHILA " + RESET + "====================== Nº ID/" + idEntrenador + " ==\n");

	    try {
	        String query = "SELECT Objeto.nombreObjeto, Mochila.cantidad FROM Mochila JOIN Objeto ON Mochila.idObjeto = Objeto.idObjeto WHERE idEntrenador = ?";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, idEntrenador);
	        ResultSet resultSet = pstmt.executeQuery();

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