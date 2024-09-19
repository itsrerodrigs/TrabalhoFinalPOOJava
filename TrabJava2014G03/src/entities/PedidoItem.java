package entities;

public class PedidoItem {
	private int idPedidoItem;
    private int idPedido;
    private int idProduto;
    private int valorDesconto;
    private double valorUnitario;
    private int quantidade;

    public PedidoItem(int idPedidoItem, int idPedido, int idProduto, int quantidade, double valorUnitario, int valorDesconto
			) {
		this.idPedidoItem = idPedidoItem;
		this.idPedido = idPedido;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.valorDesconto = valorDesconto;
    }
	public PedidoItem() {
	}

	public int getIdPedidoItem() {
		return idPedidoItem;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public int getValorDesconto() {
		return valorDesconto;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setIdPedidoItem(int idPedidoItem) {
		this.idPedidoItem = idPedidoItem;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public void setValorDesconto(int valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return "PedidoItem [idPedidoItem=" + idPedidoItem + ", idPedido=" + idPedido + ", idProduto=" + idProduto
				+ ", quantidade=" + quantidade + ", valorDesconto=" + valorDesconto + ", valorUnitario=" + valorUnitario
				+ "]";
	}
}
