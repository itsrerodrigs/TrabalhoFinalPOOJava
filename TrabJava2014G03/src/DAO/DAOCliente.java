package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Cliente;
import interfaces.CRUD;

public class DAOCliente implements CRUD<Cliente> {
    private Connection connection;

    public DAOCliente(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void criar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, dataNascimento, endereco, telefone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
    }
    @Override
    public Cliente ler(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE idcliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getDate("dataNascimento"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
                );
            }
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Cliente> listar(){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	while(rs.next()){
            	System.out.println("IdCliente: " + rs.getInt("idCliente") 
            	+ "\nNome: " + rs.getString("nome") 
            	+ "\nCPF: " + rs.getString("cpf") 
            	+ "\nData de Nascimento: " + rs.getDate("dataNascimento") 
            	+ "\nEndereço: " + rs.getString("endereco") 
            	+ "\nTelefone: " + rs.getString("telefone") + "\n");
            	}
            }
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return clientes;
    }
    @Override
    public void atualizar(Cliente cliente){
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, dtnascimento = ?, endereco = ?, telefone = ? WHERE idcliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getIdCliente());
            stmt.executeUpdate();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
    }
    @Override
    public void excluir(int idCliente){
        String sql = "DELETE FROM cliente WHERE idcliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
