package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CriarBd {
    public static void criarBancoETabelas() {
        Connection connection = null;
        Statement stmt = null;

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
            
            System.out.println("Verificando se o banco de dados já existe...");
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + database + "'");
            if (rs.next()) {
                System.out.println("O banco de dados já existe! \nContinuando com o programa...\n\n");
                return;
            }
            String sqlCreateDB = "CREATE DATABASE " + database;
            stmt.executeUpdate(sqlCreateDB);
            System.out.println("Banco de dados criado com sucesso.");

            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            stmt = connection.createStatement();

            String sqlCreateCliente = "CREATE TABLE IF NOT EXISTS Cliente ("
                    + "idCliente SERIAL PRIMARY KEY, "
                    + "nome VARCHAR(100), "
                    + "cpf VARCHAR(15), "
                    + "dataNascimento DATE, "
                    + "endereco VARCHAR(255), "
                    + "telefone VARCHAR(15))";

            String sqlCreateProduto = "CREATE TABLE IF NOT EXISTS Produto ("
                    + "idProduto SERIAL PRIMARY KEY, "
            		+ "nome VARCHAR(100), "
                    + "descricao VARCHAR(200), "
                    + "valorCusto DECIMAL(10, 2), "
                    + "valorVenda DECIMAL(10, 2), "
                    + "categoria VARCHAR(50))";

            String sqlCreatePedido = "CREATE TABLE IF NOT EXISTS Pedido ("
                    + "idPedido SERIAL PRIMARY KEY, "
                    + "dataEmissao DATE, "
                    + "dataEntrega DATE, "
                    + "valorTotal DECIMAL(10, 2), "
                    + "observacao TEXT, "
                    + "idCliente INT, "
                    + "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente))";

            String sqlCreatePedidoItens = "CREATE TABLE IF NOT EXISTS PedidoItem ("
                    + "idPedidoItem SERIAL PRIMARY KEY, "
                    + "idPedido INT, "
                    + "idProduto INT, "
                    + "valorUnitario DECIMAL(10, 2), "
                    + "quantidade INT, "
                    + "valorDesconto INT, "
                    + "FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido), "
                    + "FOREIGN KEY (idProduto) REFERENCES Produto(idProduto))";

            stmt.executeUpdate(sqlCreateCliente);
            stmt.executeUpdate(sqlCreateProduto);
            stmt.executeUpdate(sqlCreatePedido);
            stmt.executeUpdate(sqlCreatePedidoItens);
        
            String inserirCliente = "INSERT INTO Cliente (nome, cpf, dataNascimento, endereco, telefone) "
                    + "VALUES ('Lucas Coco', '123.456.789-00', '2005-09-26', 'Rua A, 100', '21999999999'),"
            		+ "('Samuel Teldison', '987.654.321-00', '1990-07-22', 'Rua B, 50', '21987654321'),"
            		+ "('Isabella Pinheiro', '321.654.987-00', '1988-11-05', 'Rua C, 200', '11912345678'),"
            		+ "('Renata Rodrigues', '456.789.123-00', '1992-03-18', 'Rua D, 300', '31987654321'),"
            		+ "('Andressa Assis', '654.987.321-00', '1994-08-30', 'Rua E, 400', '11956781234'),"
            		+ "('Eric Duarte', '789.123.654-00', '1982-12-12', 'Rua F, 500', '21999887766'),"
		            + "('Eduarda Pinho', '789.123.654-00', '1982-12-12', 'Rua F, 500', '21999887766');";
            stmt.executeUpdate(inserirCliente);

            String inserirProduto = "INSERT INTO Produto (nome, descricao, valorCusto, valorVenda, categoria) "
            		+ "VALUES ('Notebook', 'Notebook de alta performance com 16GB de RAM e SSD de 512GB', 1500.00, 2000.00, 'Eletrônicos'),"
             		+ "('Smartphone', 'Smartphone com câmera de 48MP e bateria de longa duração', 1200.00, 1800.00, 'Eletrônicos'),"
             		+ "('Tablet', 'Tablet com tela de 10 polegadas e 64GB de armazenamento', 900.00, 1500.00, 'Eletrônicos'),"
             		+ "('TV 50 polegadas', 'TV LED 4K de 50 polegadas com HDR', 2500.00, 3200.00, 'Eletrônicos'),"
             		+ "('Fone de Ouvido', 'Fone de ouvido Bluetooth com cancelamento de ruído', 200.00, 500.00, 'Acessórios'),"
             		+ "('Smartwatch', 'Smartwatch com monitoramento de atividades físicas e GPS', 700.00, 1200.00, 'Eletrônicos');";
            
            stmt.executeUpdate(inserirProduto);

            System.out.println("Banco de dados e tabelas criados com sucesso!");

        } 
        catch (SQLException | IOException e) {
            System.out.println("Erro ao criar o banco de dados ou tabelas.");
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
