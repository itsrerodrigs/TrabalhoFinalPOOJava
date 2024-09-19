package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import entities.Pedido;
import interfaces.CRUD;

public class DAOPedido implements CRUD<Pedido> {
    private Connection connection;

    public DAOPedido(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void criar(Pedido pedido) {
        String sql = "INSERT INTO pedido (dataEmissao, dataEntrega, valorTotal, observacao, idCliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(pedido.getDataEmissao()));
            stmt.setDate(2, Date.valueOf(pedido.getDataEntrega()));
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getObservacao());
            stmt.setInt(5, pedido.getIdCliente());
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			System.out.println("Erro ao criar pedido: " + e.getMessage());
		}
    }
    @Override
    public Pedido ler(int idPedido){
        String sql = "SELECT * FROM pedido WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                    rs.getInt("idPedido"),
                    rs.getInt("idCliente"),
                    rs.getDate("dataEmissao").toLocalDate(),
                    rs.getDate("dataEntrega").toLocalDate(),
                    rs.getString("observacao"),
                    rs.getDouble("valorTotal")
                );
            }
        }
		catch (SQLException e) {
			System.out.println("Erro ao ler pedido: " + e.getMessage());
		}
        return null;
    }
    @Override
    public List<Pedido> listar(){
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidos.add(new Pedido(
                    rs.getInt("idPedido"),
                    rs.getInt("idCliente"),
                    rs.getDate("dataEntrega").toLocalDate(),
                    rs.getDate("dataEmissao").toLocalDate(),
                    rs.getString("observacao"),
                    rs.getDouble("valorTotal")
                ));
            }
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        return pedidos;
    }
    @Override
    public void atualizar(Pedido pedido){
        String sql = "UPDATE pedido SET dataEmissao = ?, dataEntrega = ?, valorTotal = ?, observacao = ?, idCliente = ? WHERE idPedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(pedido.getDataEmissao()));
            stmt.setDate(2, Date.valueOf(pedido.getDataEntrega()));
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getObservacao());
            stmt.setInt(5, pedido.getIdCliente());
            stmt.setInt(6, pedido.getIdPedido());
            stmt.executeUpdate();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
    }
    @Override
    public void excluir(int idPedido){
        String sql = "DELETE FROM pedido WHERE idPedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
    }  
    public void imprimirPedido(int idPedido, boolean incluirProdutos) throws SQLException {
       
        String sqlPedido = "SELECT * FROM pedido WHERE idPedido = ?";
        
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, idPedido);
            ResultSet rsPedido = stmtPedido.executeQuery();
            
            if (rsPedido.next()) {
                System.out.println("===== Detalhes do Pedido =====");
                System.out.println("Pedido ID: " + rsPedido.getInt("idPedido"));
                System.out.println("Data Emissão: " + rsPedido.getDate("dataEmissao"));
                System.out.println("Data Entrega: " + rsPedido.getDate("dataEntrega"));
                System.out.println("Valor Total: " + rsPedido.getDouble("valorTotal"));
                System.out.println("Observação: " + rsPedido.getString("observacao"));

                if (incluirProdutos) {
                   
                    String sqlProdutos = "SELECT * FROM pedidoItem WHERE idPedido = ?";
                    
                    try (PreparedStatement stmtProdutos = connection.prepareStatement(sqlProdutos)) {
                        stmtProdutos.setInt(1, idPedido);
                        ResultSet rsProdutos = stmtProdutos.executeQuery();
                        
                        System.out.println("===== Produtos do Pedido =====");
                        
                        while (rsProdutos.next()) {
                            System.out.println("Produto ID: " + rsProdutos.getInt("idProduto"));
                            System.out.println("Quantidade: " + rsProdutos.getInt("quantidade"));
                            System.out.println("Valor Unitário: " + rsProdutos.getDouble("valorUnitario"));
                            System.out.println("Desconto: " + rsProdutos.getDouble("valorDesconto"));
                            System.out.println("----------------------------");
                        }
                    }
					catch (SQLException e) {
						System.out.println("Erro ao imprimir produtos do pedido: \n" + e.getMessage());
						throw e;
					}
                }
            }
            else {
            	System.out.println("Pedido não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao imprimir o pedido: \n" + e.getMessage());
            throw e;
        }
    }

}
