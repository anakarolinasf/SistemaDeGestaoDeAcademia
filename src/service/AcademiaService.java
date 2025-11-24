package service;
import java.sql.Date;
import java.util.List;
import dao.*;
import model.*;

public class AcademiaService {
    private AlunoDAO alunoDAO = new AlunoDAO();
    private InstrutorDAO instrutorDAO = new InstrutorDAO();
    private PlanoTreinoDAO planoDAO = new PlanoTreinoDAO();
    private FrequenciaDAO freqDAO = new FrequenciaDAO();

    // ALUNO
    public void cadastrarAluno(String nome, String cpf, Date data) {
        alunoDAO.inserir(new Aluno(nome, cpf, data));
    }
    public List<Aluno> getAlunos() {
        return alunoDAO.listar();
    }

    // INSTRUTOR
    public void cadastrarInstrutor(String nome, String esp) {
        instrutorDAO.inserir(new Instrutor(nome, esp));
    }
    public List<Instrutor> getInstrutores() {
        return instrutorDAO.listar();
    }

    // PLANO
    public void cadastrarPlano(int idAluno, int idInstrutor, String desc, int semanas) {
        planoDAO.inserir(new PlanoTreino(idAluno, idInstrutor, desc, semanas));
    }
    public List<PlanoTreino> getPlanos() {
        return planoDAO.listar();
    }

    // FREQUÊNCIA
    public void registrarFrequencia(int idAluno, Date data, boolean presenca) {
        freqDAO.inserir(new Frequencia(idAluno, data, presenca));
    }
    public List<Frequencia> getFrequencias() {
        return freqDAO.listar();
    }


    public void atualizarInstrutor(int id, String nome, String esp) {
        Instrutor i = new Instrutor(nome, esp);
        i.setId(id);
        instrutorDAO.atualizar(i);
    }
    public void excluirInstrutor(int id) {
        instrutorDAO.excluir(id);
    }

    // NOVOS MÉTODOS DE ALUNO
    public void atualizarAluno(int id, String nome, String cpf, Date data) {
        Aluno a = new Aluno(nome, cpf, data);
        a.setId(id);
        alunoDAO.atualizar(a);
    }
    public void excluirAluno(int id) {
        alunoDAO.excluir(id);
    }

    // NOVOS MÉTODOS DE PLANO
    public void atualizarPlano(int idPlano, int idAluno, int idInstrutor, String desc, int semanas) {
        PlanoTreino p = new PlanoTreino(idAluno, idInstrutor, desc, semanas);
        p.setId(idPlano);
        planoDAO.atualizar(p);
    }
    public void excluirPlano(int id) {
        planoDAO.excluir(id);
    }

    //NOVOS MÉTODOS DE FREQUENCIA
    public void atualizarFrequencia(int idFreq, int idAluno, Date data, boolean presenca) {
        Frequencia f = new Frequencia(idAluno, data, presenca);
        f.setId(idFreq);
        freqDAO.atualizar(f);
    }
    public void excluirFrequencia(int id) {
        freqDAO.excluir(id);
    }

}

