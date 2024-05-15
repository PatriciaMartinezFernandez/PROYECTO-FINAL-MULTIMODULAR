package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionPokemon {
	
	static Scanner sc = new Scanner(System.in);

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

}
