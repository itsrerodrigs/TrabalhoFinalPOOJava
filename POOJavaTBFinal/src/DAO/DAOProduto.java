package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Produto;
import interfaces.CRUD;

public class DAOProduto implements CRUD<Produto> {
    private Connection connection;

    public DAOProduto(Connection connection) {
        this.connection = connection;
    }
    
	@Override
	public void criar(Produto produto) {
		String sql = "INSERT INTO produto (nome, descricao, categoria, valorCusto, valorVenda) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCategoria());
			stmt.setDouble(4, produto.getValorCusto());
			stmt.setDouble(5, produto.getValorVenda());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao adicionar o produto.", e);
		}
		
	}
	@Override
	public Produto ler(int idProduto) {
		String sql = "SELECT * FROM produto WHERE idProduto = ?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Produto(
                    rs.getInt("idProduto"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("categoria"),
                    rs.getDouble("valorCusto"),
                    rs.getDouble("valorVenda")
                );
            }
        }
		catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar o produto.", e);
        }
        return null;
    }
	@Override
	public void atualizar(Produto produto) {
		String sql = "UPDATE produto SET nome =?, descricao = ?, categoria = ?, valorCusto = ?, valorVenda = ? WHERE idProduto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getCategoria());
            stmt.setDouble(4, produto.getValorCusto());
            stmt.setDouble(5, produto.getValorVenda());
            stmt.setInt(6, produto.getIdProduto());
            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o produto.", e);
        }
    }
	@Override
	public void excluir(int idProduto) {
		String sql = "DELETE FROM produto WHERE idProduto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir o produto.", e);
        }
    }
	@Override
	public List<Produto> listar() {
		String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
        	ResultSet rs = stmt.executeQuery()) {
    		if(rs.next()){
	            while (rs.next()) {
	            	System.out.println("IdProduto: " + rs.getInt("idProduto")
	            		+ "\nNome: " + rs.getString("nome")
	            		+ "\nCategoria: " + rs.getString("categoria")
	            		+ "\nDescrição: " + rs.getString("descricao")
	            		+ "\nValor de Custo: " + rs.getDouble("valorCusto")
	            		+ "\nValor de Venda: " + rs.getDouble("valorVenda") + "\n");
	            }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar os produtos.", e);
        }
        return produtos;
    }

}
