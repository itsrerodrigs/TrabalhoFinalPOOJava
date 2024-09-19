package entities;

public class Produto {
    private int idProduto;
    private String nome;
    private String descricao;
    private String categoria;
    private double valorCusto;
    private double valorVenda;

    public Produto(int idProduto, String nome, String descricao, String categoria, double valorCusto,
			double valorVenda) {
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.valorCusto = valorCusto;
		this.valorVenda = valorVenda;
	}
    public Produto() {
    	
    }

	public int getIdProduto() {
		return idProduto;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public double getValorCusto() {
		return valorCusto;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setValorCusto(double valorCusto) {
		this.valorCusto = valorCusto;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	@Override
	public String toString() {
		return "Produto [idProduto=" + idProduto + ", nome=" + nome +", descricao=" + descricao
				+ ", categoria=" + categoria + ", valorCusto=" + valorCusto + ", valorVenda=" + valorVenda + "]";
	}
}
