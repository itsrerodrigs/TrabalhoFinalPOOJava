package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ApagarBd {
	public static void apagarBanco() {
        Connection connection = null;
        Statement stmt = null;
        
        System.out.println("Tentando apagar banco de dados...");
        try {
            Properties props = new Properties();
            FileInputStream input = new FileInputStream("config.ini");
            props.load(input);

            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String database = props.getProperty("database");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            String url = "jdbc:postgresql://" + host + ":" + port + "/postgres";

            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();

            String sqlTerminate = "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + database + "' AND pid <> pg_backend_pid()";
            stmt.execute(sqlTerminate);

            String sqlDropDB = "DROP DATABASE IF EXISTS " + database;
            stmt.executeUpdate(sqlDropDB);

            System.out.println("Banco de dados apagado com sucesso.\n");
        }
        catch (SQLException | IOException e) {
            System.out.println("Erro ao apagar o banco de dados.");
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
