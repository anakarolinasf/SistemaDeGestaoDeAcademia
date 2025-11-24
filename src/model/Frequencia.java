package model;
import java.sql.Date;

public class Frequencia {
    private int id;
    private int idAluno;
    private Date data;
    private boolean presenca;

    public Frequencia() {}
    public Frequencia(int idAluno, Date data, boolean presenca) {
        this.idAluno = idAluno;
        this.data = data;
        this.presenca = presenca;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdAluno() {

        return idAluno;
    }
    public void setIdAluno(int idAluno) {

        this.idAluno = idAluno;
    }
    public Date getData() {

        return data;
    }
    public void setData(Date data) {

        this.data = data;
    }
    public boolean isPresenca() {

        return presenca;
    }
    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }

    @Override
    public String toString() {
        return "Aluno " + idAluno + " - " + data + " - " + (presenca ? "Presente" : "Faltou");
    }
}
