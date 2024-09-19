package entities;

import java.time.LocalDate;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private LocalDate dataEntrega;
    private LocalDate dataEmissao;
    private String observacao;
    private double valorTotal;

    public Pedido(int idPedido, int idCliente, LocalDate dataEmissao, LocalDate dataEntrega,
			String observacao, double valorTotal) {
		super();
		this.idPedido = idPedido;
		this.idCliente = idCliente;
		this.dataEntrega = dataEntrega;
		this.dataEmissao = dataEmissao;
		this.observacao = observacao;
		this.valorTotal = valorTotal;
	}
    public Pedido() {
    	
    }
    
	public int getIdPedido() {
		return idPedido;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public LocalDate getDataEntrega() {
		return dataEntrega;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public String getObservacao() {
		return observacao;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", cliente=" + idCliente +", dataEntrega=" + dataEntrega
				+ ", dataEmissao=" + dataEmissao + ", observacao=" + observacao + ", valorTotal=" + valorTotal + "]";
	}
}
