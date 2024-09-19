package entities;

import java.util.Date;

public class Cliente extends Pessoa {
    private int idCliente;
    private String endereco;
    private String telefone;
    
    public Cliente(int idCliente, String nome, String cpf, Date dataNascimento, String endereco, String telefone) {
		super(nome, cpf, dataNascimento);
		this.idCliente = idCliente;
		this.endereco = endereco;
		this.telefone = telefone;
	}
    public Cliente() {
    	
    }
	
	public int getIdCliente() {
		return idCliente;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}



