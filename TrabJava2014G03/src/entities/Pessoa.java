package entities;

import java.util.Date;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private Date dataNascimento;

    public Pessoa(String nome, String cpf, Date dtnascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dtnascimento;
    }

    public Pessoa() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dtnascimento) {
        this.dataNascimento= dtnascimento;
    }

    @Override
    public String toString() {
        return "Pessoa " +" [Nome=" + nome + ", CPF=" + cpf + ", Data de Nascimento=" + dataNascimento + "]";
    }
}

