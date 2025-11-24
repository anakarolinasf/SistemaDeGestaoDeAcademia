package model;
import java.sql.Date;

public class Aluno {
    private int id;
    private String nome;
    private String cpf;
    private Date dataIngresso;

    public Aluno() {}
    public Aluno(String nome, String cpf, Date dataIngresso) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataIngresso = dataIngresso;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public Date getDataIngresso() {
        return dataIngresso;
    }
    public void setDataIngresso(Date dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + cpf + ")";
    }
}
