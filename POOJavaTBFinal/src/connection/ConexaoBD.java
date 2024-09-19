package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexaoBD {
	 public ConexaoBD() {
    	 if (isConnectionClosed()) {
             try {
                 Properties props = new Properties();
                 FileInputStream input = new FileInputStream("config.ini");
                 props.load(input);

                 String host = props.getProperty("host");
                 String port = props.getProperty("port");
                 String database = props.getProperty("database");
                 String user = props.getProperty("user");
                 String password = props.getProperty("password");
                 
                 String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
                 connection = DriverManager.getConnection(url, user, password);
             } 
             catch (SQLException | IOException e) {
                 System.out.println("Erro ao conectar ao banco de dados.");
                 e.printStackTrace();
             }
         }
	}
	private Connection connection = null;

	public  Connection getConnection() {
		try {
			return connection;
		}
		catch(Exception e) {
			System.out.println("Erro ao conectar ao banco de dados.");
			e.printStackTrace();
			return null;
		}
	}
    public  boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    public  void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada com sucesso.\n");
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados.");
            e.printStackTrace();
        }
    }
    
    public void retirarAcessoBd() {
        Connection connection = null;
        Statement stmt = null;
        
        System.out.println("Removendo acesso dos usuarios ao banco de dados...");
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

            String sqlRevoke = "REVOKE ALL PRIVILEGES ON DATABASE " + database + " FROM PUBLIC";
            stmt.executeUpdate(sqlRevoke);

            System.out.println("Acesso removido com sucesso.\n");
        } 
        catch (SQLException | IOException e) {
            System.out.println("Erro ao revogar o acesso ao banco de dados.");
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

