package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.PedidoItem;
import interfaces.CRUD;

public class DAOPedidoItem implements CRUD<PedidoItem> {
    private Connection connection;

    public DAOPedidoItem(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void criar(PedidoItem item){
    	String sqlPedido = "SELECT * FROM Pedido WHERE idPedido = ?";
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, item.getIdPedido());
            ResultSet rs = stmtPedido.executeQuery();
            if (!rs.next()) {
                System.out.println("Pedido com id " + item.getIdPedido() + " não encontrado.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar pedido: " + e.getMessage());
            return;
        }
    	
    	String sql = "INSERT INTO PedidoItem (idPedido, idProduto, valorUnitario, quantidade, valorDesconto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdPedidoItem());
            stmt.setInt(2, item.getIdProduto());
            stmt.setDouble(3, item.getValorUnitario());
            stmt.setInt(4, item.getQuantidade());
            stmt.setInt(5, item.getValorDesconto());
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			System.out.println("Erro ao criar item de pedido: " + e.getMessage());
		}
    }

    @Override
    public PedidoItem ler(int idPedidoItem){
        String sql = "SELECT * FROM PedidoItem WHERE idPedidoItem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedidoItem);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PedidoItem(
                    rs.getInt("idPedidoItem"),
                    rs.getInt("idPedido"),
                    rs.getInt("idProduto"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valorUnitario"),
                    rs.getInt("valorDesconto")
                );
            }
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PedidoItem> listar(){
        List<PedidoItem> itens = new ArrayList<>();
        String sql = "SELECT * FROM PedidoItem";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                itens.add(new PedidoItem(
                    rs.getInt("idPedidoItem"),
                    rs.getInt("idPedido"),
                    rs.getInt("idProduto"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valorUnitario"),
                    rs.getInt("valorDesconto")
                ));
            }
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
        return itens;
    }

    @Override
    public void atualizar(PedidoItem item){
        String sql = "UPDATE PedidoItem SET idPedido = ?, idProduto = ?, valorUnitario = ?, quantidade = ?, valorDesconto = ? WHERE idpedidoitem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdPedido());
            stmt.setInt(2, item.getIdProduto());
            stmt.setDouble(3, item.getValorUnitario());
            stmt.setInt(4, item.getQuantidade());
            stmt.setInt(5, item.getValorDesconto());
            stmt.setInt(6, item.getIdPedidoItem());
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void excluir(int idPedidoItem){
        String sql = "DELETE FROM PedidoItem WHERE idPedidoItem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedidoItem);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
    }
    
    public PedidoItem buscarPorPedidoEProduto(int idPedido, int idProduto){
        String sql = "SELECT * FROM PedidoItem WHERE idPedido = ? AND idProduto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PedidoItem(
                    rs.getInt("idPedidoitem"),
                    rs.getInt("idpedido"),
                    rs.getInt("idProduto"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valorUnitario"),
                    rs.getInt("valorDesconto")
                );
            }
        }
		catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }

    public void excluirTodos(int idPedido){
        String sql = "DELETE FROM PedidoItem WHERE idPedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
		catch (SQLException e) {
			e.printStackTrace();
        }
    }
    
    public void excluirPorPedido(int idPedido){
        String sql = "DELETE FROM PedidoItem WHERE idPedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir itens do pedido: " + e.getMessage());
        }
    }

    public List<PedidoItem> buscarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT * FROM PedidoItem WHERE idPedido = ?";
        List<PedidoItem> itens = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PedidoItem item = new PedidoItem();
                item.setIdPedido(rs.getInt("idPedido"));
                item.setIdProduto(rs.getInt("idProduto"));
                item.setValorUnitario(rs.getDouble("valorUnitario"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setValorDesconto(rs.getInt("valorDesconto"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens do pedido: " + e.getMessage());
            throw e;
        }
        
        return itens;
    }
}
