package model;

public class PlanoTreino {
    private int id;
    private int idAluno;
    private int idInstrutor;
    private String descricao;
    private int duracaoSemanas;

    public PlanoTreino() {}
    public PlanoTreino(int idAluno, int idInstrutor, String descricao, int duracaoSemanas) {
        this.idAluno = idAluno;
        this.idInstrutor = idInstrutor;
        this.descricao = descricao;
        this.duracaoSemanas = duracaoSemanas;
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
    public int getIdInstrutor() {
        return idInstrutor;
    }
    public void setIdInstrutor(int idInstrutor) {
        this.idInstrutor = idInstrutor;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getDuracaoSemanas() {
        return duracaoSemanas;
    }
    public void setDuracaoSemanas(int duracaoSemanas) {
        this.duracaoSemanas = duracaoSemanas;
    }

    @Override
    public String toString() {
        return id + " - Plano: " + descricao + " (" + duracaoSemanas + " semanas)";
    }
}
