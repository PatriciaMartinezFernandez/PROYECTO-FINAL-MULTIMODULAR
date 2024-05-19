package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Gestion {
	
	protected static Scanner sc = new Scanner(System.in);
	protected static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	protected static String user = "root";
	protected static String password = "usuariodam";
	protected static final String AMARILLO = "\u001B[33m";
	protected static final String RESET = "\u001B[0m";

	protected static Connection connection;

	static {
		try {
			setConnection(DriverManager.getConnection(url, user, password));
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}
	}
	
	
	public static Connection getConnection() {
		return connection;
	}
	public static void setConnection(Connection connection) {
		Gestion.connection = connection;
	}

}
