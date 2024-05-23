package game;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionPokemon extends Gestion {

    static int idPokemon;
    private static Scanner sc = new Scanner(System.in);

    public static void elegirPokemon() {
        System.out.println("\nLista de Pokémon:");
        String queryPokemon = "SELECT * FROM VistaPokemons";
        try (PreparedStatement stmtPokemon = connection.prepareStatement(queryPokemon)) {
            ResultSet rsPokemon = stmtPokemon.executeQuery();
            int count = 0;
            while (rsPokemon.next()) {
                if (count % 10 == 0 && count != 0) {
                    System.out.println();
                }
                System.out.print(rsPokemon.getInt("idPokemon") + ". " + rsPokemon.getString("nombrePokemon") + "\t");
                count++;
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta SQL: " + e.getMessage());
        }

        System.out.print("\nIntroduce el ID o nombre del Pokémon del que quieres ver información: ");
        String pokemonInput = sc.nextLine().trim();

        try {
            idPokemon = Integer.parseInt(pokemonInput);
        } catch (NumberFormatException e) {
            idPokemon = -1;
        }

        if (idPokemon != -1) {
            infoPokedex(idPokemon);
        } else {
            try {
                idPokemon = buscarIdPokemonPorNombre(pokemonInput);
            } catch (SQLException e) {
                e.getMessage();
            }
            if (idPokemon != -1) {
                infoPokedex(idPokemon);
            } else {
                System.out.println("No se encontró ningún Pokémon con ese nombre.");
            }
        }
    }

    private static void infoPokedex(int idPokemon) {
        try {
            String callProcedure = "{CALL ObtenerInfoPokemonPorID(?)}";
            CallableStatement cstmt = connection.prepareCall(callProcedure);
            cstmt.setInt(1, idPokemon);
            ResultSet rsInfo = cstmt.executeQuery();
            if (rsInfo.next()) {
                idPokemon = rsInfo.getInt("idPokemon");
                String nombrePokemon = rsInfo.getString("nombrePokemon");
                String tipoPrimario = rsInfo.getString("tipoPrimario");
                String tipoSecundario = rsInfo.getString("tipoSecundario");
                double altura = rsInfo.getDouble("altura");
                double peso = rsInfo.getDouble("peso");
                String descripcion = rsInfo.getString("descripcion");

                fichaPokemon(idPokemon, nombrePokemon, tipoPrimario, tipoSecundario, altura, peso, descripcion);

            } else {
                System.out.println("No se encontró ningún Pokémon con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta SQL: " + e.getMessage());
        }
    }

    private static void fichaPokemon(int idPokemon, String nombrePokemon, String tipoPrimario, String tipoSecundario,
            double altura, double peso, String descripcion) {
        if (tipoSecundario == null) {
            tipoSecundario = "";
        }

        System.out.println("\n==" + AMARILLO + " POKÉDEX " + RESET
                + "==================================================================================================\r\n" + "\n"
                + idPokemon + ". " + nombrePokemon + "\n" + "---------------------------\n" + tipoPrimario + "   "
                + tipoSecundario + "\nAltuta: " + altura + "m" + "\nPeso: " + peso + "kg"
                + "\n\n=============================================================================================================\r"
                + "\n\n" + descripcion
                + "\n\n=============================================================================================================\r\n"
                + "\n");
    }

    private static int buscarIdPokemonPorNombre(String nombrePokemon) throws SQLException {
        try {
            String callProcedure = "{CALL ObtenerIDPokemonPorNombre(?)}";
            CallableStatement cstmt = connection.prepareCall(callProcedure);
            cstmt.setString(1, nombrePokemon);
            ResultSet rsIdPokemon = cstmt.executeQuery();
            if (rsIdPokemon.next()) {
                return rsIdPokemon.getInt("idPokemon");
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta SQL: " + e.getMessage());
        }
        return -1;
    }
}
