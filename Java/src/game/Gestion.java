package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * La clase Gestion proporciona métodos y atributos comunes utilizados en varias
 * partes del juego. Contiene métodos para establecer y obtener la conexión a la
 * base de datos, así como constantes para colores de texto en la consola.
 * 
 * @author Jaime Martínez Fernández
 */

public class Gestion {

	protected static Scanner sc = new Scanner(System.in);
	protected static String url = "jdbc:mysql://localhost:3306/PkmnTrainers";
	protected static String user = "root";
	protected static String password = "usuariodam";
	protected static final String AMARILLO = "\u001B[33m";
	protected static final String RESET = "\u001B[0m";

	protected static Connection connection;

	/**
	 * Constructor estático que establece la conexión a la base de datos al
	 * inicializar la clase.
	 */

	static {
		try {
			setConnection(DriverManager.getConnection(url, user, password));
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

	/**
	 * Devuelve la conexión a la base de datos.
	 * 
	 * @return Conexxión a la base de datos
	 */
	
	@SuppressWarnings("exports")
	public static Connection getConnection() {
		return connection;
	}

	/**
	 * Establece la conexión a la base de datos.
	 * 
	 * @param connection Conexión a la base de datos
	 */
	
	@SuppressWarnings("exports")
	public static void setConnection(Connection connection) {
		Gestion.connection = connection;
	}

}
